package com.gigaspaces.persistency;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.gigaspaces.client.ReadByIdsResult;
import com.gigaspaces.itest.model.IssuePojo;

public class BasicMongoSpaceLRUTest extends AbstractSystemTestUnit {

	@Override
	public void test() {
		List<IssuePojo> issuePojos = new LinkedList<IssuePojo>();
		for (int i = 0; i < 15; i++) {
			issuePojos.add(new IssuePojo(i, "dank" + i));
		}

		gigaSpace.writeMultiple(issuePojos.toArray(new IssuePojo[] {}));
		waitForEmptyReplicationBacklogAndClearMemory(gigaSpace);

		IssuePojo[] pojos = gigaSpace.readMultiple(new IssuePojo(), 20);
		// /Assert.assertEquivalent("issue pojos", issuePojos,
		// Arrays.asList(pojos));

		waitForEmptyReplicationBacklogAndClearMemory(gigaSpace);
		IssuePojo result = gigaSpace.readById(IssuePojo.class, 1);
		// Assert.assertEquals("Bad issue read", issuePojos.get(1), result);

		// readByIds
		waitForEmptyReplicationBacklogAndClearMemory(gigaSpace);
		ReadByIdsResult<IssuePojo> readByIds = gigaSpace.readByIds(
				IssuePojo.class, new Object[] { 1 });
		IssuePojo[] resultsArray = readByIds.getResultsArray();
		// Assert.assertEquals("Bad issue read", issuePojos.get(1),
		// resultsArray[0]);

		// empty readById
		result = gigaSpace.readById(IssuePojo.class, 100);
		//Assert.assertNull("Bad issue read", result);

		// empty readByIds
		readByIds = gigaSpace.readByIds(IssuePojo.class, new Object[] { 100 });
		resultsArray = readByIds.getResultsArray();
		//Assert.assertNull("Bad issue read", resultsArray[0]);
	}

}
