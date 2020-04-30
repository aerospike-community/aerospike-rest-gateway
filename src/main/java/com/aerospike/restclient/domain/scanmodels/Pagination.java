/*
 * Copyright 2020 Aerospike, Inc.
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
package com.aerospike.restclient.domain.scanmodels;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Pagination")
public class Pagination {

    @ApiModelProperty(value = "The next page token.")
    private String nextToken;

    @ApiModelProperty(value = "The total number of records in page.")
    private long totalRecords;

    public Pagination() {
    }

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String token) {
        nextToken = token;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long total) {
        totalRecords = total;
    }

}
