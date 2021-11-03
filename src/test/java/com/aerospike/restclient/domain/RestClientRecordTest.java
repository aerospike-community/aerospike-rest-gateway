/*
 * Copyright 2019 Aerospike, Inc.
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
package com.aerospike.restclient.domain;

import java.util.HashMap;
import java.util.Map;

import com.aerospike.client.Record;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RestClientRecordTest {

	@Test
	public void testNoArgConstructor() {
		new RestClientRecord();
	}

	@Test
	public void testNullBins() {
		Record record = new Record(null, 2, 1000);
		RestClientRecord rcRecord = new RestClientRecord(record);

		assertNull(rcRecord.bins);
		assertEquals(rcRecord.generation, 2);
		assertEquals(rcRecord.ttl, record.getTimeToLive());
	}

	@Test
	public void testWithBins() {
		Map<String, Object>bins = new HashMap<>();
		bins.put("bin1", 5L);
		bins.put("bin2", "hello");
		Record record = new Record(bins, 2, 1000);
		RestClientRecord rcRecord = new RestClientRecord(record);

		assertEquals(rcRecord.bins, bins);
		assertEquals(rcRecord.generation, 2);
		assertEquals(rcRecord.ttl, record.getTimeToLive());
	}
}
