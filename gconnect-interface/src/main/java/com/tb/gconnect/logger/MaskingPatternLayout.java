package com.tb.gconnect.logger;


import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.tb.gconnect.util.StringUtils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskingPatternLayout extends PatternLayout {
    private String patternsProperty;
    private Optional<Pattern> pattern;
    private static final int MAX_DEAL_LENGTH = 100;
    private static final char jsonChargeChar = '"';
    private static final char jsonChargeChar2 = ':';

    public MaskingPatternLayout() {
    }

    public String getPatternsProperty() {
        return this.patternsProperty;
    }

    public void setPatternsProperty(String patternsProperty) {
        this.patternsProperty = patternsProperty;
        if (this.patternsProperty != null) {
            this.pattern = Optional.of(Pattern.compile(patternsProperty, 8));
        } else {
            this.pattern = Optional.empty();
        }

    }

    public String doLayout(ILoggingEvent event) {
        StringBuilder message = new StringBuilder(super.doLayout(event));
        int length = message.length();
        if (this.pattern.isPresent()) {
            Matcher matcher = ((Pattern)this.pattern.get()).matcher(message);

            while(matcher.find()) {
                for(int group = 1; group <= matcher.groupCount(); ++group) {
                    if (matcher.group(group) != null) {
                        int matcherEnd = matcher.end(group);
                        int jsonChargeIndex = matcherEnd + 2;
                        if (length >= jsonChargeIndex) {
                            char chargeChar = message.charAt(jsonChargeIndex);
                            int searchCharIndex;
                            int nextChar;
                            String padStr;
                            if ('"' == chargeChar) {
                                searchCharIndex = matcherEnd + 3;
                                if (length < searchCharIndex) {
                                    break;
                                }

                                nextChar = message.indexOf("\"", searchCharIndex);
                                if (nextChar - searchCharIndex > 100 || nextChar - searchCharIndex == 0) {
                                    break;
                                }

                                padStr = StringUtils.leftPad("*", nextChar - (jsonChargeIndex + 1), "*");
                                message.replace(jsonChargeIndex + 1, nextChar, padStr);
                            } else if (':' == chargeChar) {
                                searchCharIndex = matcherEnd + 5;
                                if (length < searchCharIndex) {
                                    break;
                                }

                                nextChar = message.indexOf("\"", searchCharIndex);
                                if (nextChar - searchCharIndex > 100 || nextChar - searchCharIndex == 1) {
                                    break;
                                }

                                padStr = StringUtils.leftPad("*", nextChar - (jsonChargeIndex + 4), "*");
                                message.replace(jsonChargeIndex + 3, nextChar - 1, padStr);
                            }
                        }
                    }
                }
            }
        }

        return message.toString();
    }
}
