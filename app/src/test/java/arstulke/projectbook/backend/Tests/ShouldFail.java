package arstulke.projectbook.backend.Tests;

import org.junit.Assert;

abstract class ShouldFail {

    public abstract void failCode();

    public void test(String failMessage, Exception exceptionType) {
        try {
            failCode();
            Assert.fail(failMessage);
        } catch (Exception e) {
            if (exceptionType != null) {
                if (e.getClass().getName().equals(exceptionType.getClass().getName())) {
                    Assert.assertEquals(0, 0);
                } else {
                    Assert.fail("[FALSE ERROR]\nSHOULD BE: \n"+exceptionType.getClass().getName()+"\nIS ACTUALLY: \n"+e.getClass().getName());
                }
            }else{
                Assert.assertEquals(0, 0);
            }
        }
    }

    public void test(Exception e) {
        test("should fail", e);
    }

    public void test() {
        test(null);
    }
}