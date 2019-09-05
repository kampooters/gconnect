package com.tb.gconnect.security.authentication.oauth2.parameter;

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2010-2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

abstract interface MultivaluedMap<K, V> extends Map<K, List<V>> {
	public abstract void putSingle(K paramK, V paramV);

	public abstract void add(K paramK, V paramV);

	public abstract V getFirst(K paramK);

	public abstract void addAll(K paramK, V[] paramArrayOfV);

	public abstract void addAll(K paramK, List<V> paramList);

	public abstract void addFirst(K paramK, V paramV);

	public abstract boolean equalsIgnoreValueOrder(
            MultivaluedMap<K, V> paramMultivaluedMap);
}

/**
 * Abstract skeleton implementation of a {@link MultivaluedMap} that is backed
 * by a [key, multi-value] store represented as a {@link Map Map&lt;K,
 * List&lt;V&gt;&gt;}.
 * 
 * @param <K>
 *            the type of keys maintained by this map.
 * @param <V>
 *            the type of mapped values.
 */
abstract class AbstractMultivaluedMap<K, V> implements MultivaluedMap<K, V> {

	/**
	 * Backing store for the [key, multi-value] pairs.
	 */
	protected final Map<K, List<V>> store;

	/**
	 * Initialize the backing store in the abstract parent multivalued map
	 * implementation.
	 * 
	 * @param store
	 *            the backing {@link Map} to be used as a [key, multi-value]
	 *            store. Must not be {@code null}.
	 * @throws NullPointerException
	 *             in case the underlying {@code store} parameter is
	 *             {@code null}.
	 */
	public AbstractMultivaluedMap(Map<K, List<V>> store) {
		if (store == null) {
			throw new NullPointerException(
					"Underlying store must not be 'null'.");
		}
		this.store = store;
	}

	/**
	 * Set the value for the key to be a one item list consisting of the
	 * supplied value. Any existing values will be replaced.
	 * <p />
	 * NOTE: This implementation ignores {@code null} values; A supplied value
	 * of {@code null} is ignored and not added to the purged value list. As a
	 * result of such operation, empty value list would be registered for the
	 * supplied key. Overriding implementations may modify this behavior by
	 * redefining the {@link #addNull(List)} method.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the single value of the key. If the value is {@code null} it
	 *            will be ignored.
	 */
	@Override
	public final void putSingle(K key, V value) {
		List<V> values = getValues(key);

		values.clear();
		if (value != null) {
			values.add(value);
		} else {
			addNull(values);
		}
	}

	/**
	 * Define the behavior for adding a {@code null} values to the value list.
	 * <p />
	 * Default implementation is a no-op, i.e. the {@code null} values are
	 * ignored. Overriding implementations may modify this behavior by providing
	 * their own definitions of this method.
	 * 
	 * @param values
	 *            value list where the {@code null} value addition is being
	 *            requested.
	 */
	@SuppressWarnings("UnusedParameters")
	protected void addNull(List<V> values) {
		// do nothing in the default implementation; ignore the null value
	}

	/**
	 * Define the behavior for adding a {@code null} values to the first
	 * position in the value list.
	 * <p />
	 * Default implementation is a no-op, i.e. the {@code null} values are
	 * ignored. Overriding implementations may modify this behavior by providing
	 * their own definitions of this method.
	 * 
//	 * @param values
	 *            value list where the {@code null} value addition is being
	 *            requested.
	 */
	@SuppressWarnings("UnusedParameters")
	protected void addFirstNull(List<V> values) {
		// do nothing in the default implementation; ignore the null value
	}

	/**
	 * Add a value to the current list of values for the supplied key.
	 * <p />
	 * NOTE: This implementation ignores {@code null} values; A supplied value
	 * of {@code null} is ignored and not added to the value list. Overriding
	 * implementations may modify this behavior by redefining the
	 * {@link #addNull(List)} method.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value to be added.
	 */
	@Override
	public final void add(K key, V value) {
		List<V> values = getValues(key);

		if (value != null) {
			values.add(value);
		} else {
			addNull(values);
		}
	}

	/**
	 * Add multiple values to the current list of values for the supplied key.
	 * If the supplied array of new values is empty, method returns immediately.
	 * Method throws a {@code NullPointerException} if the supplied array of
	 * values is {@code null}.
	 * <p />
	 * NOTE: This implementation ignores {@code null} values; Any of the
	 * supplied values of {@code null} is ignored and not added to the value
	 * list. Overriding implementations may modify this behavior by redefining
	 * the {@link #addNull(List)} method.
	 * 
	 * @param key
	 *            the key.
	 * @param newValues
	 *            the values to be added.
	 * @throws NullPointerException
	 *             if the supplied array of new values is {@code null}.
	 */
	@Override
	public final void addAll(K key, V... newValues) {
		if (newValues == null) {
			throw new NullPointerException(
					"Supplied array of values must not be null.");
		}
		if (newValues.length == 0) {
			return;
		}

		List<V> values = getValues(key);

		for (V value : newValues) {
			if (value != null) {
				values.add(value);
			} else {
				addNull(values);
			}
		}
	}

	/**
	 * Add all the values from the supplied value list to the current list of
	 * values for the supplied key. If the supplied value list is empty, method
	 * returns immediately. Method throws a {@code NullPointerException} if the
	 * supplied array of values is {@code null}.
	 * <p />
	 * NOTE: This implementation ignores {@code null} values; Any {@code null}
	 * value in the supplied value list is ignored and not added to the value
	 * list. Overriding implementations may modify this behavior by redefining
	 * the {@link #addNull(List)} method.
	 * 
	 * @param key
	 *            the key.
	 * @param valueList
	 *            the list of values to be added.
	 * @throws NullPointerException
	 *             if the supplied value list is {@code null}.
	 */
	@Override
	public final void addAll(K key, List<V> valueList) {
		if (valueList == null) {
			throw new NullPointerException(
					"Supplied list of values must not be null.");
		}
		if (valueList.isEmpty()) {
			return;
		}

		List<V> values = getValues(key);

		for (V value : valueList) {
			if (value != null) {
				values.add(value);
			} else {
				addNull(values);
			}
		}
	}

	@Override
	public final V getFirst(K key) {
		List<V> values = store.get(key);
		if (values != null && values.size() > 0) {
			return values.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Add a value to the first position in the current list of values for the
	 * supplied key.
	 * <p />
	 * NOTE: This implementation ignores {@code null} values; A supplied value
	 * of {@code null} is ignored and not added to the purged value list.
	 * Overriding implementations may modify this behavior by redefining the
	 * {@link #addFirstNull(List)} method.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value to be added.
	 */
	@Override
	public final void addFirst(K key, V value) {
		List<V> values = getValues(key);

		if (value != null) {
			values.add(0, value);
		} else {
			addFirstNull(values);
		}
	}

	/**
	 * Return a non-null list of values for a given key. The returned list may
	 * be empty.
	 * <p />
	 * If there is no entry for the key in the map, a new empty {@link List}
	 * instance is created, registered within the map to hold the values of the
	 * key and returned from the method.
	 * 
	 * @param key
	 *            the key.
	 * @return value list registered with the key. The method is guaranteed to
	 *         never return {@code null}.
	 */
	protected final List<V> getValues(K key) {
		List<V> l = store.get(key);
		if (l == null) {
			l = new LinkedList<V>();
			store.put(key, l);
		}
		return l;
	}

	@Override
	public String toString() {
		return store.toString();
	}

	/**
	 * {@inheritDoc }
	 * <p />
	 * This implementation delegates the method call to to the the underlying
	 * [key, multi-value] store.
	 * 
	 * @return a hash code value for the underlying [key, multi-value] store.
	 */
	@Override
	public int hashCode() {
		return store.hashCode();
	}

	/**
	 * {@inheritDoc }
	 * <p />
	 * This implementation delegates the method call to to the the underlying
	 * [key, multi-value] store.
	 * 
	 * @return {@code true} if the specified object is equal to the underlying
	 *         [key, multi-value] store, {@code false} otherwise.
	 */
	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(Object o) {
		return store.equals(o);
	}

	@Override
	public Collection<List<V>> values() {
		return store.values();
	}

	@Override
	public int size() {
		return store.size();
	}

	@Override
	public List<V> remove(Object key) {
		return store.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends List<V>> m) {
		store.putAll(m);
	}

	@Override
	public List<V> put(K key, List<V> value) {
		return store.put(key, value);
	}

	@Override
	public Set<K> keySet() {
		return store.keySet();
	}

	@Override
	public boolean isEmpty() {
		return store.isEmpty();
	}

	@Override
	public List<V> get(Object key) {
		return store.get(key);
	}

	@Override
	public Set<Entry<K, List<V>>> entrySet() {
		return store.entrySet();
	}

	@Override
	public boolean containsValue(Object value) {
		return store.containsValue(value);
	}

	@Override
	public boolean containsKey(Object key) {
		return store.containsKey(key);
	}

	@Override
	public void clear() {
		store.clear();
	}

	@Override
	public boolean equalsIgnoreValueOrder(MultivaluedMap<K, V> omap) {
		if (this == omap) {
			return true;
		}
		if (!keySet().equals(omap.keySet())) {
			return false;
		}
		for (Entry<K, List<V>> e : entrySet()) {
			List<V> olist = omap.get(e.getKey());
			if (e.getValue().size() != olist.size()) {
				return false;
			}
			for (V v : e.getValue()) {
				if (!olist.contains(v)) {
					return false;
				}
			}
		}
		return true;
	}
}

/**
 * A hash table based implementation of {@link MultivaluedMap} interface.
 * 
 * This implementation provides all of the optional map operations. This class
 * makes no guarantees as to the order of the map; in particular, it does not
 * guarantee that the order will remain constant over time. The implementation
 * permits {@code null} key. By default the implementation does also permit
 * {@code null} values, but ignores them. This behavior can be customized by
 * overriding the protected {@link #addNull(List) addNull(...)} and
 * {@link #addFirstNull(List) addFirstNull(...)} methods.
 *
 *
 *
 * This implementation provides constant-time performance for the basic
 * operations (<tt>get</tt> and <tt>put</tt>), assuming the hash function
 * disperses the elements properly among the buckets. Iteration over collection
 * views requires time proportional to the "capacity" of the map instance (the
 * number of buckets) plus its size (the number of key-value mappings). Thus,
 * it's very important not to set the initial capacity too high (or the load
 * factor too low) if iteration performance is important.
 *
 * An instance of <tt>MultivaluedHashMap</tt> has two parameters that affect its
 * performance: <i>initial capacity</i> and <i>load factor</i>. The
 * <i>capacity</i> is the number of buckets in the hash table, and the initial
 * capacity is simply the capacity at the time the hash table is created. The
 * <i>load factor</i> is a measure of how full the hash table is allowed to get
 * before its capacity is automatically increased. When the number of entries in
 * the hash table exceeds the product of the load factor and the current
 * capacity, the hash table is <i>rehashed</i> (that is, internal data
 * structures are rebuilt) so that the hash table has approximately twice the
 * number of buckets.
 *
 * As a general rule, the default load factor (.75) offers a good tradeoff
 * between time and space costs. Higher values decrease the space overhead but
 * increase the lookup cost (reflected in most of the operations of the
 * <tt>HashMap</tt> class, including <tt>get</tt> and <tt>put</tt>). The
 * expected number of entries in the map and its load factor should be taken
 * into account when setting its initial capacity, so as to minimize the number
 * of rehash operations. If the initial capacity is greater than the maximum
 * number of entries divided by the load factor, no rehash operations will ever
 * occur.
 *
 * If many mappings are to be stored in a <tt>MultivaluedHashMap</tt> instance,
 * creating it with a sufficiently large capacity will allow the mappings to be
 * stored more efficiently than letting it perform automatic rehashing as needed
 * to grow the table.
 *
 * <strong>Note that this implementation is not guaranteed to be
 * synchronized.</strong> If multiple threads access a hash map concurrently,
 * and at least one of the threads modifies the map structurally, it <i>must</i>
 * be synchronized externally. (A structural modification is any operation that
 * adds or deletes one or more mappings; merely changing the value associated
 * with a key that an instance already contains is not a structural
 * modification.) This is typically accomplished by synchronizing on some object
 * that naturally encapsulates the map.
 *
 * The iterators returned by all of this class's "collection view methods" are
 * <i>fail-fast</i>: if the map is structurally modified at any time after the
 * iterator is created, in any way except through the iterator's own
 * <tt>remove</tt> method, the iterator will throw a
 * {@link ConcurrentModificationException}. Thus, in the face of concurrent
 * modification, the iterator fails quickly and cleanly, rather than risking
 * arbitrary, non-deterministic behavior at an undetermined time in the future.
 *
 * Note that the fail-fast behavior of an iterator cannot be guaranteed as it
 * is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification. Fail-fast iterators throw
 * <tt>ConcurrentModificationException</tt> on a best-effort basis. Therefore,
 * it would be wrong to write a program that depended on this exception for its
 * correctness: <i>the fail-fast behavior of iterators should be used only to
 * detect bugs.</i>
 * 
 * @param <K>
 *            the type of keys maintained by this map.
 * @param <V>
 *            the type of mapped values.
 */
class MultivaluedHashMap<K, V> extends AbstractMultivaluedMap<K, V> implements
		Serializable {

	private static final long serialVersionUID = -6052320403766368902L;

	/**
	 * Constructs an empty multivalued hash map with the default initial
	 * capacity ({@code 16}) and the default load factor ({@code 0.75}).
	 */
	public MultivaluedHashMap() {
		super(new HashMap<K, List<V>>());
	}

	/**
	 * Constructs an empty multivalued hash map with the specified initial
	 * capacity and the default load factor ({@code 0.75}).
	 * 
	 * @param initialCapacity
	 *            the initial capacity.
	 * @throws IllegalArgumentException
	 *             if the initial capacity is negative.
	 */
	public MultivaluedHashMap(int initialCapacity) {
		super(new HashMap<K, List<V>>(initialCapacity));
	}

	/**
	 * Constructs an empty multivalued hash map with the specified initial
	 * capacity and load factor.
	 * 
	 * @param initialCapacity
	 *            the initial capacity
	 * @param loadFactor
	 *            the load factor
	 * @throws IllegalArgumentException
	 *             if the initial capacity is negative or the load factor is
	 *             nonpositive
	 */
	public MultivaluedHashMap(int initialCapacity, float loadFactor) {
		super(new HashMap<K, List<V>>(initialCapacity, loadFactor));
	}

	/**
	 * Constructs a new multivalued hash map with the same mappings as the
	 * specified {@link MultivaluedMap }. The {@link List} instances holding the
	 * values of each key are created anew instead of being reused.
	 * 
	 * @param map
	 *            the multivalued map whose mappings are to be placed in this
	 *            multivalued map.
	 * @throws NullPointerException
	 *             if the specified map is {@code null}
	 */
	public MultivaluedHashMap(MultivaluedMap<? extends K, ? extends V> map) {
		this();
		putAll(map);
	}

	/**
	 * This private method is used by the copy constructor to avoid exposing
	 * additional generic parameters through the public API documentation.
	 * 
	 * @param <T>
	 *            any subclass of K
	 * @param <U>
	 *            any subclass of V
	 * @param map
	 *            the map
	 */
	private <T extends K, U extends V> void putAll(MultivaluedMap<T, U> map) {
		for (Entry<T, List<U>> e : map.entrySet()) {
			store.put(e.getKey(), new ArrayList<V>(e.getValue()));
		}
	}

	/**
	 * Constructs a new multivalued hash map with the same mappings as the
	 * specified single-valued {@link Map }.
	 * 
	 * @param map
	 *            the single-valued map whose mappings are to be placed in this
	 *            multivalued map.
	 * @throws NullPointerException
	 *             if the specified map is {@code null}
	 */
	public MultivaluedHashMap(Map<? extends K, ? extends V> map) {
		this();
		for (Entry<? extends K, ? extends V> e : map.entrySet()) {
			this.putSingle(e.getKey(), e.getValue());
		}
	}
}

/**
 * An implementation of {@link MultivaluedMap} where keys and values are
 * instances of String.
 * \n
 * This map has an additional ability to instantiate classes using the
 * individual string values as a constructor parameters.
 * 
 */
class MultivaluedStringMap extends MultivaluedHashMap<String, String> {

	static final long serialVersionUID = -6052320403766368902L;

	public MultivaluedStringMap(
			MultivaluedMap<? extends String, ? extends String> map) {
		super(map);
	}

	public MultivaluedStringMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public MultivaluedStringMap(int initialCapacity) {
		super(initialCapacity);
	}

	public MultivaluedStringMap() {
		super();
	}

	@Override
	protected void addFirstNull(List<String> values) {
		values.add("");
	}

	@Override
	protected void addNull(List<String> values) {
		values.add(0, "");
	}

	public final <A> A getFirst(String key, Class<A> type) {
		String value = getFirst(key);
		if (value == null) {
			return null;
		}
		Constructor<A> c = null;
		try {
			c = type.getConstructor(String.class);
		} catch (Exception ex) {
			throw new IllegalArgumentException(type.getName()
					+ " has no String constructor", ex);
		}
		A retVal = null;
		try {
			retVal = c.newInstance(value);
		} catch (Exception ex) {
		}
		return retVal;
	}

	@SuppressWarnings("unchecked")
	public final <A> A getFirst(String key, A defaultValue) {
		String value = getFirst(key);
		if (value == null) {
			return defaultValue;
		}

		Class<A> type = (Class<A>) defaultValue.getClass();

		Constructor<A> c = null;
		try {
			c = type.getConstructor(String.class);
		} catch (Exception ex) {
			throw new IllegalArgumentException(type.getName()
					+ " has no String constructor", ex);
		}
		A retVal = defaultValue;
		try {
			retVal = c.newInstance(value);
		} catch (Exception ex) {
		}
		return retVal;
	}
}
