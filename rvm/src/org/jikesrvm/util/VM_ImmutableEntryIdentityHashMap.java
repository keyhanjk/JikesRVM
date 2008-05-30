/*
 *  This file is part of the Jikes RVM project (http://jikesrvm.org).
 *
 *  This file is licensed to You under the Common Public License (CPL);
 *  You may not use this file except in compliance with the License. You
 *  may obtain a copy of the License at
 *
 *      http://www.opensource.org/licenses/cpl1.0.php
 *
 *  See the COPYRIGHT.txt file distributed with this work for information
 *  regarding copyright ownership.
 */
package org.jikesrvm.util;

import org.jikesrvm.util.VM_ImmutableEntryHashMap.Bucket;

/**
 * A hash map with entirely immutable buckets. It doesn't correctly support
 * remove, and its values cannot be mutated by a put with the same key.
 */
public final class VM_ImmutableEntryIdentityHashMap<K, V> extends VM_AbstractHashMap<K,V> {

  @Override
  AbstractBucket<K,V> createNewBucket(K key, V value, AbstractBucket<K, V> next) {
    return new Bucket<K,V>(key, value, next);
  }

  public VM_ImmutableEntryIdentityHashMap() {
    super(DEFAULT_SIZE);
  }

  public VM_ImmutableEntryIdentityHashMap(int size) {
    super(size);
  }

  @Override
  protected boolean same(K k1, K k2) {
    return k1 == k2;
  }
}