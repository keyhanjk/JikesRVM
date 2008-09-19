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
package org.mmtk.harness.lang.pcode;

import java.util.List;

import org.mmtk.harness.lang.compiler.Register;

public abstract class CallOp extends EnnaryOp {

  public CallOp(Register resultTemp, List<Register> params) {
    super("call", resultTemp, params);
  }

  public CallOp(List<Register> params) {
    super("call", params);
  }

  public boolean affectsControlFlow() {
    return true;
  }
}