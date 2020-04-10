/***********/
/* PACKAGE */
/***********/
package IR;

import MIPS.sir_MIPS_a_lot;
import Register_Allocation.register_allocation;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

public class IR
{
	private IRcommand dataHead=null;
	private IRcommandList dataTail=null;
	
	private IRcommand head= new IRcommand_Empty();
	private IRcommandList tail= new IRcommandList(new IRcommand_Empty(),null);

	public  IRcommandList start_of_main = new IRcommandList(new IRcommand_Empty(),null);

	public IRcommandList getEndOfListIRCommands() {
		IRcommandList cmd_lst = tail;
		while (cmd_lst.tail != null){
			cmd_lst = cmd_lst.tail;
		}
		return cmd_lst;
	}

	/******************/
	/* Add IR command */
	/******************/
	public void Add_IRcommand(IRcommand cmd)
	{
		if ((head == null) && (tail == null))
		{
			this.head = cmd;
		}
		else if ((head != null) && (tail == null))
		{
			this.tail = new IRcommandList(cmd,null);
		}
		else
		{
			IRcommandList it = tail;
			while (it.tail != null)
			{
				it = it.tail;
			}
			it.tail = new IRcommandList(cmd,null);
		}
	}
	
	public void Add_Data_IRcommand(IRcommand cmd)
	{
		if ((dataHead == null) && (dataTail == null))
		{
			this.dataHead = cmd;
		}
		else if ((dataHead != null) && (dataTail == null))
		{
			this.dataTail = new IRcommandList(cmd,null);
		}
		else
		{
			IRcommandList it = dataTail;
			while (it.tail != null)
			{
				it = it.tail;
			}
			it.tail = new IRcommandList(cmd,null);
		}
	}

	public void Add_Start_Of_Main_IRcommand(IRcommand cmd)
	{
		IRcommandList it = start_of_main;
		while (it.tail != null)
		{
			it = it.tail;
		}
		it.tail = new IRcommandList(cmd,null);
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		if (dataHead != null) dataHead.MIPSme();
		if (dataTail != null) dataTail.MIPSme();
		sir_MIPS_a_lot.getInstance().startTextSection();
		if (head != null) head.MIPSme();
		if (tail != null) tail.MIPSme();
	}

	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static IR instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected IR() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static IR getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new IR();
		}
		return instance;
	}
}
