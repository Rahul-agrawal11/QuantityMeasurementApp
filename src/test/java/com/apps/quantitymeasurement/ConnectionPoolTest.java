package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.util.ConnectionPool;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ConnectionPoolTest {

	@Test
	public void testConnectionPool_Initialization() {

		ConnectionPool pool = ConnectionPool.getInstance();

		assertNotNull(pool);
	}
	
	@Test
	public void testConnectionPool_Acquire_Release() throws Exception {

	    ConnectionPool pool = ConnectionPool.getInstance();

	    java.sql.Connection conn = pool.getConnection();

	    assertNotNull(conn);

	    pool.releaseConnection(conn);
	}
	
	
}