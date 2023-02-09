/* TEMPLATE GENERATED TESTCASE FILE
Filename: CWE191_Integer_Underflow__long_rand_postdec_71b.java
Label Definition File: CWE191_Integer_Underflow.label.xml
Template File: sources-sinks-71b.tmpl.java
*/
/*
 * @description
 * CWE: 191 Integer Underflow
 * BadSource: rand Set data to result of rand()
 * GoodSource: A hardcoded non-zero, non-min, non-max, even number
 * Sinks: decrement
 *    GoodSink: Ensure there will not be an underflow before decrementing data
 *    BadSink : Decrement data, which can cause an Underflow
 * Flow Variant: 71 Data flow: data passed as an Object reference argument from one method to another in different classes in the same package
 *
 * */

package testcases.CWE191_Integer_Underflow.s04;
import testcasesupport.*;

public class CWE191_Integer_Underflow__long_rand_postdec_71b
{
    public void badSink(Object dataObject ) throws Throwable
    {
        long data = (Long)dataObject;

        /* POTENTIAL FLAW: if data == Long.MIN_VALUE, this will overflow */
        data--;
        long result = (long)(data);

        IO.writeLine("result: " + result);

    }

    /* goodG2B() - use goodsource and badsink */
    public void goodG2BSink(Object dataObject ) throws Throwable
    {
        long data = (Long)dataObject;

        /* POTENTIAL FLAW: if data == Long.MIN_VALUE, this will overflow */
        data--;
        long result = (long)(data);

        IO.writeLine("result: " + result);

    }

    /* goodB2G() - use badsource and goodsink */
    public void goodB2GSink(Object dataObject ) throws Throwable
    {
        long data = (Long)dataObject;

        /* FIX: Add a check to prevent an underflow from occurring */
        if (data > Long.MIN_VALUE)
        {
            data--;
            long result = (long)(data);
            IO.writeLine("result: " + result);
        }
        else
        {
            IO.writeLine("data value is too small to decrement.");
        }

    }
}
