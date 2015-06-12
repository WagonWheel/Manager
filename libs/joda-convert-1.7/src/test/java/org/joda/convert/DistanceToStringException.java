/*
 *  Copyright 2010-present Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.joda.convert;

import java.text.ParseException;

/**
 * Example class with annotated methods.
 */
public class DistanceToStringException {

    /** Amount. */
    final int amount;

    @FromString
    public static DistanceToStringException parse(String amount) {
        amount = amount.substring(0, amount.length() - 1);
        return new DistanceToStringException(Integer.parseInt(amount));
    }

    public DistanceToStringException(int amount) {
        this.amount = amount;
    }

    @ToString
    public String print() throws ParseException {
        throw new ParseException("Test", 2);
    }

    @Override
    public String toString() {
        return "Distance[" + amount + "m]";
    }

}
