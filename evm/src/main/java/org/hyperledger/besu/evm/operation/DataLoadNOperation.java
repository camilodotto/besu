/*
 * Copyright contributors to Hyperledger Besu
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

import org.hyperledger.besu.evm.Code;
import org.hyperledger.besu.evm.EVM;
import org.hyperledger.besu.evm.frame.MessageFrame;
import org.hyperledger.besu.evm.gascalculator.GasCalculator;

import org.apache.tuweni.bytes.Bytes;

/** The Data load operation. */
public class DataLoadNOperation extends AbstractFixedCostOperation {

  /**
   * Instantiates a new Data Load operation.
   *
   * @param gasCalculator the gas calculator
   */
  public DataLoadNOperation(final GasCalculator gasCalculator) {
    super(0xBA, "DATALOADN", 0, 1, gasCalculator, gasCalculator.getVeryLowTierGasCost());
  }

  @Override
  public OperationResult executeFixedCostOperation(final MessageFrame frame, final EVM evm) {
    final Code code = frame.getCode();
    final byte[] codeBytes = code.getBytes().toArrayUnsafe();
    int index = readBigEndianU16(frame.getPC() + 1, codeBytes);

    final Bytes data = code.getData(index, 32);
    frame.pushStackItem(data);

    return successResponse;
  }
}
