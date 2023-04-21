/*
 * Copyright ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.hyperledger.besu.evm.operation;

import static org.hyperledger.besu.evm.internal.Words.readBigEndianU16;

import org.hyperledger.besu.evm.EVM;
import org.hyperledger.besu.evm.frame.MessageFrame;
import org.hyperledger.besu.evm.gascalculator.GasCalculator;

import org.apache.tuweni.bytes.Bytes;

/** The Dup operation. */
public class SwapNOperation extends AbstractFixedCostOperation {

  /** The Swap operation success result. */
  static final OperationResult swapSuccess = new OperationResult(3, null);

  /**
   * Instantiates a new SwapN operation.
   *
   * @param gasCalculator the gas calculator
   */
  public SwapNOperation(final GasCalculator gasCalculator) {
    super(0xb6, "SWAPN", 0, 1, gasCalculator, gasCalculator.getVeryLowTierGasCost());
  }

  @Override
  public Operation.OperationResult executeFixedCostOperation(
      final MessageFrame frame, final EVM evm) {
    final byte[] code = frame.getCode().getBytes().toArrayUnsafe();
    return staticOperation(frame, code, frame.getPC());
  }

  /**
   * Performs swap N operation.
   *
   * @param frame the frame
   * @param code the code
   * @param pc the pc
   * @return the operation result
   */
  public static OperationResult staticOperation(
      final MessageFrame frame, final byte[] code, final int pc) {
    int index = readBigEndianU16(pc + 1, code);

    final Bytes tmp = frame.getStackItem(0);
    frame.setStackItem(0, frame.getStackItem(index));
    frame.setStackItem(index, tmp);

    return swapSuccess;
  }
}
