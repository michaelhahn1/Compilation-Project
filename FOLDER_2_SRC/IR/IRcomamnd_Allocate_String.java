package IR;

import MIPS.sir_MIPS_a_lot;

public class IRcomamnd_Allocate_String extends IRcommand {
	String var_name;
	String str;
	Boolean is_func;
	public IRcomamnd_Allocate_String(String var_name, String str,boolean is_func, int line)
	{
		this.var_name = var_name;
		this.str = str;
		this.line = line;
		this.is_func = is_func;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		if (is_func) {
			sir_MIPS_a_lot.getInstance().allocateFuncString(var_name, str,line);

		} else {
			sir_MIPS_a_lot.getInstance().allocateString(var_name, str,line);

		}
	}
}
