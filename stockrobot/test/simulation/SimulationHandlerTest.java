package simulation;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import database.jpa.IJPAHelper;
import database.jpa.JPAHelper;
import database.jpa.JPAHelperSimulator;
import database.jpa.tables.AlgorithmEntity;

public class SimulationHandlerTest {
	private static SimulationHandler simulationHandler = null;
	private static IJPAHelper jpaHelper = null;
	@BeforeClass
	public static void beforeClass(){ //First of all
		jpaHelper = new JPAHelperSimulator();
		simulationHandler = new SimulationHandler();
	}
	@Test
	public void test() {
		simulationHandler.clearTestDatabase();
		simulationHandler.simulateAlgorithm(new AlgorithmEntity("Algorithm1", "algorithms.TestAlgorithm"));
		simulationHandler.clearTestDatabase();
	}
/*
 * <openjpa-2.2.0-r422266:1244990 nonfatal user error> org.apache.openjpa.persistence.InvalidStateException: Encountered unmanaged object "database.jpa.tables.StockPrices-761" in life cycle state  unmanaged while cascading persistence via field "database.jpa.tables.PortfolioHistory.stockPrice" during flush.  However, this field does not allow cascade persist. You cannot flush unmanaged objects or graphs that have persistent associations to unmanaged objects.
 Suggested actions: a) Set the cascade attribute for this field to CascadeType.PERSIST or CascadeType.ALL (JPA annotations) or "persist" or "all" (JPA orm.xml), 
 b) enable cascade-persist globally, 
 c) manually persist the related field value prior to flushing. 
 d) if the reference belongs to another context, allow reference to it by setting StoreContext.setAllowReferenceToSiblingContext().
FailedObject: database.jpa.tables.StockPrices-761
	at org.apache.openjpa.kernel.SingleFieldManager.preFlushPC(SingleFieldManager.java:786)
	at org.apache.openjpa.kernel.SingleFieldManager.preFlush(SingleFieldManager.java:621)
	at org.apache.openjpa.kernel.SingleFieldManager.preFlush(SingleFieldManager.java:589)
	at org.apache.openjpa.kernel.SingleFieldManager.preFlush(SingleFieldManager.java:505)
	at org.apache.openjpa.kernel.StateManagerImpl.preFlush(StateManagerImpl.java:2982)
	at org.apache.openjpa.kernel.PNewState.beforeFlush(PNewState.java:40)
	at org.apache.openjpa.kernel.StateManagerImpl.beforeFlush(StateManagerImpl.java:1054)
	at org.apache.openjpa.kernel.BrokerImpl.flush(BrokerImpl.java:2112)
	at org.apache.openjpa.kernel.BrokerImpl.flushSafe(BrokerImpl.java:2072)
	at org.apache.openjpa.kernel.BrokerImpl.beforeCompletion(BrokerImpl.java:1990)
	at org.apache.openjpa.kernel.LocalManagedRuntime.commit(LocalManagedRuntime.java:81)
	at org.apache.openjpa.kernel.BrokerImpl.commit(BrokerImpl.java:1514)
	at org.apache.openjpa.kernel.DelegatingBroker.commit(DelegatingBroker.java:933)
	at org.apache.openjpa.persistence.EntityManagerImpl.commit(EntityManagerImpl.java:570)
	at database.jpa.JPAHelperBase.storeObject(JPAHelperBase.java:266)
	at database.jpa.JPAHelperSimulator.storeObject(JPAHelperSimulator.java:1)
	at simulation.TraderSimulator2.buyStock(TraderSimulator2.java:41)
	at algorithms.TestAlgorithm.buyStock(TestAlgorithm.java:102)
	at algorithms.TestAlgorithm.update(TestAlgorithm.java:94)
	at simulation.SimulationHandler.updateAlgorithm(SimulationHandler.java:152)
	at simulation.SimulationHandler.simulateAlgorithm(SimulationHandler.java:108)
	at simulation.SimulationHandlerTest.test(SimulationHandlerTest.java:24)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:616)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:44)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:41)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:20)
	at org.junit.runners.BlockJUnit4ClassRunner.runNotIgnored(BlockJUnit4ClassRunner.java:79)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:71)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:49)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:193)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:52)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:191)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:42)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:184)
	at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:28)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:236)
	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:50)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:467)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:683)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:390)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:197)



 */
}
