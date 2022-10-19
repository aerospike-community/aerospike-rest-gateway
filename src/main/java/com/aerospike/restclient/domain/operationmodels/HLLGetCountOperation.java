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

import com.aerospike.restclient.util.AerospikeAPIConstants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Server returns estimated number of elements in the HLL bin.",
        externalDocs = @ExternalDocumentation(url = "https://javadoc.io/doc/com.aerospike/aerospike-client/" + AerospikeAPIConstants.AS_CLIENT_VERSION + "/com/aerospike/client/operation/HLLOperation.html")
)
public class HLLGetCountOperation extends HLLOperation {
    @Schema(
            description = "The type of operation. It is always " + OperationTypes.HLL_COUNT,
            required = true,
            allowableValues = OperationTypes.HLL_COUNT
    )
    final public static String type = OperationTypes.HLL_COUNT;

    @JsonCreator
    public HLLGetCountOperation(@JsonProperty(value = "binName", required = true) String binName) {
        super(binName);
    }

    @Override
    public com.aerospike.client.Operation toOperation() {
        return com.aerospike.client.operation.HLLOperation.getCount(binName);
    }
}
