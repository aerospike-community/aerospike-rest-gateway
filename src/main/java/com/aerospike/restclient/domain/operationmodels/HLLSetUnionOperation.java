/*
 * Copyright 2022 Aerospike, Inc.
 *
 * Portions may be licensed to Aerospike, Inc. under one or more contributor
 * license agreements WHICH ARE COMPATIBLE WITH THE APACHE LICENSE, VERSION 2.0.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.aerospike.restclient.domain.operationmodels;

import com.aerospike.client.Value;
import com.aerospike.client.operation.HLLPolicy;
import com.aerospike.restclient.util.AerospikeAPIConstants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        description = "Server sets union of specified HLL objects with HLL bin. Server does not return a value.",
        externalDocs = @ExternalDocumentation(url = "https://javadoc.io/doc/com.aerospike/aerospike-client/" + AerospikeAPIConstants.AS_CLIENT_VERSION + "/com/aerospike/client/operation/HLLOperation.html")
)
public class HLLSetUnionOperation extends HLLOperation {
    @Schema(
            description = "The type of operation. It is always " + OperationTypes.HLL_SET_UNION,
            required = true,
            allowableValues = OperationTypes.HLL_SET_UNION
    )
    final public static String type = OperationTypes.HLL_SET_UNION;

    @Schema(required = true)
    private final List<byte[]> values;

    @JsonCreator
    public HLLSetUnionOperation(@JsonProperty("binName") String binName, @JsonProperty("values") List<byte[]> values) {
        super(binName);
        this.values = values;
    }

    @Override
    public com.aerospike.client.Operation toOperation() {
        List<Value.HLLValue> asVals = values.stream().map(Value.HLLValue::new).toList();
        return com.aerospike.client.operation.HLLOperation.setUnion(HLLPolicy.Default, binName, asVals);
    }
}
