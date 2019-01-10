/*
 * Copyright 2011-2019 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.data.neo4j.transactions;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.testutil.MultiDriverTestClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.transactions.service.ServiceA;
import org.springframework.data.neo4j.transactions.service.ServiceB;
import org.springframework.data.neo4j.transactions.service.WrapperService;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Vince Bickers
 * @see http://stackoverflow.com/questions/17224887/java-spring-transactional-method-not-rolling-back-as-expected
 * @see DATAGRAPH-602
 */
@ContextConfiguration(classes = { ApplicationConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class ExtendedTransactionsIT extends MultiDriverTestClass {

	@Autowired ServiceA serviceA;

	@Autowired ServiceB serviceB;

	@Autowired WrapperService wrapperService;

	@Test
	public void shouldRollbackSuccessThenFail() {

		try {
			wrapperService.composeSuccessThenFail();
			fail("should have thrown exception");
		} catch (Exception e) {
			assertEquals("Deliberately throwing exception", e.getLocalizedMessage());
			assertEquals(0, countNodes());
		}
	}

	@Test
	@Transactional
	@Rollback
	public void shouldCommitSuccessSuccess() {

		try {
			wrapperService.composeSuccessThenSuccess();
			assertEquals(2, countNodes());
		} catch (Exception e) {
			fail("should not have thrown exception");
		}
	}

	@Test
	public void shouldRollbackFailThenSuccess() {
		try {
			wrapperService.composeFailThenSuccess();
			fail("should have thrown exception");
		} catch (Exception e) {
			assertEquals("Deliberately throwing exception", e.getLocalizedMessage());
			assertEquals(0, countNodes());
		}
	}

	@Test
	public void shouldRollbackFailThenFail() {
		try {
			wrapperService.composeFailThenFail();
			fail("should have thrown exception");
		} catch (Exception e) {
			assertEquals("Deliberately throwing exception", e.getLocalizedMessage());
			assertEquals(0, countNodes());
		}
	}

	@Test
	public void shouldRollbackWithCheckedException() {
		try {
			wrapperService.rollbackWithCheckedException();
			fail("should have thrown exception");
		} catch (Exception e) {
			assertEquals("Deliberately throwing exception", e.getLocalizedMessage());
			assertEquals(0, countNodes());
		}
	}

	@Test
	public void shouldRollbackRepositoryMethodOnCheckedException() {
		try {
			serviceA.run();
		} catch (Exception e) {
			assertNull(serviceB.getBilbo());
		}
	}

	private int countNodes() {
		Iterator iterator = wrapperService.fetch().iterator();
		int i = 0;
		while (iterator.hasNext()) {
			iterator.next();
			i++;
		}
		return i;
	}
}