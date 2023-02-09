/* TEMPLATE GENERATED TESTCASE FILE
Filename: CWE190_Integer_Overflow__short_rand_postinc_81_bad.java
Label Definition File: CWE190_Integer_Overflow.label.xml
Template File: sources-sinks-81_bad.tmpl.java
*/
/*
 * @description
 * CWE: 190 Integer Overflow
 * BadSource: rand Set data to result of rand()
 * GoodSource: A hardcoded non-zero, non-min, non-max, even number
 * Sinks: increment
 *    GoodSink: Ensure there will not be an overflow before incrementing data
 *    BadSink : Increment data, which can cause an overflow
 * Flow Variant: 81 Data flow: data passed in a parameter to an abstract method
 *
 * */

package testcases.CWE190_Integer_Overflow.s06;
import testcasesupport.*;

import javax.servlet.http.*;

public class CWE190_Integer_Overflow__short_rand_postinc_81_bad extends CWE190_Integer_Overflow__short_rand_postinc_81_base
{
    public void action(short data ) throws Throwable
    {

        /* POTENTIAL FLAW: if data == Short.MAX_VALUE, this will overflow */
        data++;
        short result = (short)(data);

        IO.writeLine("result: " + result);

    }
}
