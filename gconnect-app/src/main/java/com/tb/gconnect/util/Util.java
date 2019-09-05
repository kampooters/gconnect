package com.tb.gconnect.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author abdul.rehman4
 * Contains the utility methods
 */
public abstract class Util {
    private static final ObjectMapper oMapper = new ObjectMapper();

//    /**
//     * author Abdulrehman
//     *
//     * Casts {@link Notification} to {@link NotificationCommonDTO}
//     * @param obj {@link Notification} object
//     * @return NotificationCommonDTO object
//     */
//    public static NotificationCommonDTO cast(Notification obj){
//        NotificationCommonDTO commonDTO = new NotificationCommonDTO();
//        if (ObjectUtils.isEmpty(obj)) {
//            return commonDTO;
//        }
//        commonDTO.setNotification_id(obj.getNotification_id());
//        commonDTO.setMessage_id(obj.getMessage_id());
//        commonDTO.setApp_id(obj.getApp_id());
//        commonDTO.setPriority(obj.getPriority());
//        commonDTO.setStatus(obj.getStatus());
//        commonDTO.setError(obj.getError());
//        commonDTO.setMessage(obj.getMessage());
//        commonDTO.setMssidn(obj.getMssidn());
//        commonDTO.setNotification_type(obj.getNotification_type());
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("compaign_id",obj.getCompaign_id());
//        map.put("start_window",obj.getStart_window());
//        map.put("end_window",obj.getEnd_window());
//        map.put("schedule_date",obj.getSchedule_date());
//        map.put("expiry_date",obj.getExpiry_date());
//        map.put("created_at",obj.getCreated_at());
//        map.put("updated_at",obj.getUpdated_at());
//        map.put("created_by",obj.getCreated_by());
//        map.put("updated_by",obj.getUpdated_by());
//        map.put("reference_id",obj.getReference_id());
//
//        commonDTO.setProps(map);
//        return commonDTO;
//    }
//
//    public static List<NotificationCommonDTO> cast(List<Notification> notifications){
//        List<NotificationCommonDTO> notificationCommonDTOs = new ArrayList<>();
//        if(CollectionUtils.isEmpty(notifications)) {
//            return notificationCommonDTOs;
//        }
//        notifications.forEach(notification -> {
//            notificationCommonDTOs.add(cast(notification));
//        });
//        return notificationCommonDTOs;
//    }

    /**
     * @author aqeel.abbas
     *
     * converts map to string
     */
    public static String convertWithStream(Map<String, ?> map) {
        String mapAsString = map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
        return mapAsString;
    }

}
