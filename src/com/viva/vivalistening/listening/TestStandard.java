/*文件名  TestStandard.java
 * 
 * 修改人  Qi-feng Tang
 * 修改时间  2014-06-17
 * 修改内容  添加范例代码
 * 
 */
package com.viva.vivalistening.listening;


/* 展示代码规范
 * @author  Qi-feng Tang
 * @version  1.0
 * @since  1.0 
 */
public class TestStandard {
	
	//静态变量
	static int SCOUNT = 15;
	static int SFALG1 = 1;
	static int SFALG2 = 2;
	static int SFALG3 = 3;
	
	//成员变量
	int count;
	
	public TestStandard(int count)
	{
		this.count = count;
	}
	

	/* 实例for语句
	 * @since 1.0
	 */
	void testFor()
	{
		
		for (int i = 0 ; i < TestStandard.SCOUNT ; i++ )
		{
			//code ....
			for (int j = 0 ; j < TestStandard.SCOUNT ; j++ )
			{
				
				//code ....
				for (int k = 0 ; k < TestStandard.SCOUNT ; k++ )
				{
					//code
				}//end of for (int k = 0 ; k < 15 ; k++ )
				
			}//end of for (int j = 0 ; j < 15 ; j++ )
			
		}//end of for (int i = 0 ; i < 15 ; i++ )
		
		
	}//end of void testStandard()
	
	
	/* 实例if语句
	 * @since 1.0
	 */
	void testIf()
	{
		int f = 0;
		//code
		
		if (f == TestStandard.SFALG1)
		{
			int f1 = 0;
			//code...
			if (f1 == TestStandard.SFALG2)
			{
			
				int f2 = 0;
				//code...
				if (f2 == TestStandard.SFALG3)
				{
					
				}//end of if (f2 == TestStandard.SFALG3)
				
				
			}//end of if(f1 == TestStandard.SFALG2)
			
			
		}//end of if(f == TestStandard.SFALG1)
		
	}//end of testIf()
	
	
	
}
