package com.ctrlaltelite.copshop;

import com.ctrlaltelite.copshop.tests.BuyerModelTests;
import com.ctrlaltelite.copshop.tests.DatabaseTests;
import com.ctrlaltelite.copshop.tests.ListingModelTests;
import com.ctrlaltelite.copshop.tests.SellerModelTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BuyerModelTests.class,
        DatabaseTests.class,
        ListingModelTests.class,
        SellerModelTests.class
})
public class AllTests
{

}
