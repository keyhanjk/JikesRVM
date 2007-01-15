/*
 * This file is part of Jikes RVM (http://jikesrvm.sourceforge.net).
 * The Jikes RVM project is distributed under the Common Public License (CPL).
 * A copy of the license is included in the distribution, and is also
 * available at http://www.opensource.org/licenses/cpl1.0.php
 *
 * (C) Copyright IBM Corp. 2001
 */
//$Id$
package com.ibm.jikesrvm.opt;

import java.lang.reflect.Constructor;
import com.ibm.jikesrvm.opt.ir.OPT_IR;
/**
 * Driver routine for dominator computation.  This phase invokes
 * the Lengauer-Tarjan dominator calculation.
 *
 * @author Michael Hind
 */
final class OPT_DominatorsPhase extends OPT_CompilerPhase {

  /**
   * Should we unfactor the CFG? 
   */
  private boolean unfactor = false;

  /**
   * @param unfactor Should we unfactor the CFG before computing
   * dominators?
   */
  public OPT_DominatorsPhase(boolean unfactor) {
	 super(new Object[]{new Boolean(unfactor)});
	 this.unfactor = unfactor;
  }

  /**
   * Constructor for this compiler phase
   */
  private static final Constructor<OPT_CompilerPhase> constructor = getCompilerPhaseConstructor("com.ibm.jikesrvm.opt.OPT_DominatorsPhase", new Class[]{Boolean.TYPE});

  /**
   * Get a constructor object for this compiler phase
   * @return compiler phase constructor
   */
  public Constructor<OPT_CompilerPhase> getClassConstructor() {
    return constructor;
  }

  /**
   * Should this phase be performed?  This is a member of a composite
   * phase, so always return true.  The parent composite phase will
   * dictate.
   * @param options controlling compiler options
   */
  public final boolean shouldPerform (OPT_Options options) {
    return true;
  }
  /**
   * Return a string representation of this phase
   * @return "Dominators + LpStrTree"
   */
  public final String getName() {
    return  "Dominators + LpStrTree";
  }

  /**
   * Should the IR be printed before and/or after this phase?
   * @param options controlling compiler options
   * @param before query control
   * @return true or false
   */
  public final boolean printingEnabled(OPT_Options options, boolean before) {
    return  false;
  }

  /**
   * Main driver for the dominator calculation.
   */
  public final void perform(OPT_IR ir) {
    try {
      // reset flags in case an exception is thrown inside "perform"
      // and it doesn't return normally
      ir.HIRInfo.dominatorsAreComputed = false;

      // compute (forward) dominators, 
      // leaves info in scratch object of basic blocks
      OPT_LTDominators.perform(ir, true, unfactor);

      // create the dominator tree, relies on dominator info being
      // in scratch object of basic blocks
      OPT_DominatorTree.perform(ir, true);

      // create the loop structured tree (LST)
      OPT_LSTGraph.perform(ir);

      // computation completed, so set flag
      ir.HIRInfo.dominatorsAreComputed = true;
    } catch (OPT_OperationNotImplementedException e) {
        OPT_Compiler.report(e.getMessage());
    }
  }
}
