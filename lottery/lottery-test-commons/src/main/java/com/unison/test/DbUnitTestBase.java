package com.unison.test;

import java.util.LinkedList;
import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.FullXmlDataFileLoader;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 支持DBUnit测试的基础类。
 * 
 * @author Yang Bo
 */
public abstract class DbUnitTestBase {
	@Autowired
	protected IDatabaseTester databaseTester;
	
	@Autowired
	protected IDatabaseTester databaseTester2;

	@Before
	public void setup() throws Exception{
		List<IDataSet> dataSets = getDataSet(getDataSetNames());
		IDataSet[] dataArray = dataSets.toArray(new IDataSet[0]);
		CompositeDataSet compositeData = new CompositeDataSet(dataArray);
		databaseTester.setDataSet(compositeData);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setTearDownOperation(DatabaseOperation.NONE);
        databaseTester.onSetup();
        
        if (databaseTester2 != null && getDataSetNames2() != null){
	        dataSets = getDataSet(getDataSetNames2());
			dataArray = dataSets.toArray(new IDataSet[0]);
			compositeData = new CompositeDataSet(dataArray);
			databaseTester2.setDataSet(compositeData);
	        databaseTester2.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
	        databaseTester2.setTearDownOperation(DatabaseOperation.NONE);
	        databaseTester2.onSetup();
        }
	}
	
	private List<IDataSet> getDataSet(List<String> datasetNames) throws Exception {
		List<IDataSet> dataSets = new LinkedList<IDataSet>();
		for (String name : datasetNames) {
			dataSets.add(new FullXmlDataFileLoader().load(name));
		}
        return dataSets;
    }

	public abstract List<String> getDataSetNames();
	public abstract List<String> getDataSetNames2();

	public IDatabaseTester getDatabaseTester() {
		return databaseTester;
	}

	public void setDatabaseTester(IDatabaseTester databaseTester) {
		this.databaseTester = databaseTester;
	}

	public IDatabaseTester getDatabaseTester2() {
		return databaseTester2;
	}

	public void setDatabaseTester2(IDatabaseTester databaseTester2) {
		this.databaseTester2 = databaseTester2;
	}
}
