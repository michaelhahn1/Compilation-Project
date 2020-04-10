/***********/
/* PACKAGE */
/***********/
package MIPS;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.io.PrintWriter;
import java.time.temporal.Temporal;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import GLOBAL_VARIABLES.GLOBAL_VARIABLES;
import TEMP.*;
import IR.*;
public class sir_MIPS_a_lot
{
	private int WORD_SIZE=4;
	/***********************/
	/* The file writer ... */
	/***********************/
	private PrintWriter fileWriter;

	/***********************/
	/* The file writer ... */
	/***********************/
	public void finalizeFile()
	{
		fileWriter.close();
	}
	public void print_int(TEMP t,int line)
	{
		fileWriter.format("\t#print_int line:%d\n",line);
		int idx=t.getSerialNumber();
		// fileWriter.format("\taddi $a0,$t%d,0\n",idx);
		fileWriter.format("\tmove $a0,$t%d\n",idx);
		fileWriter.format("\tli $v0,1\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tli $a0,32\n");  // print space
		fileWriter.format("\tli $v0,11\n");
		fileWriter.format("\tsyscall\n");
	}
	public void print_string(TEMP t, int line)
	{
		fileWriter.format("\t#print_string line:%d\n",line);
		int idx=t.getSerialNumber();
		// fileWriter.format("\taddi $a0,$t%d,0\n",idx);
		fileWriter.format("\tmove $a0,$t%d\n",idx);
		fileWriter.format("\tli $v0,4\n");
		fileWriter.format("\tsyscall\n");
	}
	//public TEMP addressLocalVar(int serialLocalVarNum)
	//{
	//	TEMP t  = TEMP_FACTORY.getInstance().getFreshTEMP();
	//	int idx = t.getSerialNumber();
	//
	//	fileWriter.format("\taddi $t%d,$fp,%d\n",idx,-serialLocalVarNum*WORD_SIZE);
	//	
	//	return t;
	//}
	public void exit(int line) {
		fileWriter.format("\tli $v0,10\t");
		fileWriter.format("\t#exit line:%d\n",line);
		fileWriter.format("\tsyscall\n");
	}
	public void allocateString(String var_name, String str,int line)
	{
		fileWriter.format("\tstring_%s: .asciiz %s\t", var_name, str);
		fileWriter.format("\t#allocateString line:%d\n",line);

	}
	public void allocateFuncString(String var_name, String str,int line)
	{
		fileWriter.format("\tfunc_%s: .asciiz \"%s\"\t", var_name, str);
		fileWriter.format("\t#allocateFuncString line:%d\n",line);

	}

	public void allocateWord(String var_name, int line){
		fileWriter.format("\tglobal_%s: .word 721\t", var_name);
		fileWriter.format("\t#allocateWord line:%d\n",line);

	}
	public void addVirtualFunctionEntry(String vfuncLabel){
		fileWriter.format("\t.word %s\n",vfuncLabel);
	}
	public void startDataSection() {
		fileWriter.format(".data\n");
	}
	public void startTextSection() {
		fileWriter.format(".text\n");
	}
	public void load(TEMP dst,String var_name, int line)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tlw $t%d,global_%s\t",idxdst,var_name);
		fileWriter.format("\t#load line:%d\n",line);

	}

	public void load_byte(TEMP dst,TEMP address,int line)
	{
		int idxdst=dst.getSerialNumber();
		int idxaddress=address.getSerialNumber();
		fileWriter.format("\tlb $t%d,0($t%d)\t",idxdst,idxaddress);
		fileWriter.format("\t#load_byte line:%d\n",line);

	}
	public void load_address(TEMP dst,String var_name,int line)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tla $t%d,global_%s\t",idxdst,var_name);
		fileWriter.format("\t#load_address line:%d\n",line);

	}

	public void load_virtual_table(TEMP dst,String cls_name,int line)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tla $t%d, Virtual_Table_%s\t",idxdst,cls_name);
		fileWriter.format("\t#load_virtual_table line:%d\n",line);

	}
	public void load_string_address(TEMP dst,String err_name, int line)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tla $t%d,string_%s\t",idxdst,err_name);
		fileWriter.format("\t#load_string_address line:%d\n",line);

	}
	public void load_func_address(TEMP dst,String err_name, int line)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tla $t%d,func_%s\t",idxdst,err_name);
		fileWriter.format("\t#load_func_address line:%d\n",line);

	}
	
	public void store(String var_name,TEMP src,int line)
	{
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tsw $t%d,global_%s\t",idxsrc,var_name);
		fileWriter.format("\t#store line:%d\n",line);

	}
	public void change_stack(int bytes,int line){
		fileWriter.format("\taddi $sp, $sp, %d\t", bytes);
		fileWriter.format("\t#change_stack line:%d\n",line);

	}
	public void store_local_sp_offset_temp(TEMP src, int sp_offset,int line)
	{
		int idxsrc=src.getSerialNumber();
		store_local_sp_offset("$t" + idxsrc, sp_offset,line);

	}
	public void store_local_fp_offset_temp(TEMP src, int fp_offset,int line)
	{
		int idxsrc=src.getSerialNumber();
		store_local_fp_offset("$t" + idxsrc, fp_offset,line);
	}

	public void load_local_fp_offset_temp(TEMP dst, int fp_offset, int line)
	{
		int idxdst=dst.getSerialNumber();
		load_local_fp_offset("$t" + idxdst, fp_offset,line);

	}

	public void load_local_sp_offset_temp(TEMP dst, int sp_offset,int line)
	{
		int idxdst=dst.getSerialNumber();
		load_local_sp_offset("$t" + idxdst, sp_offset,line);

	}

	public void call_function(String function_label,int line){
		fileWriter.format("\tjal %s\t", function_label);
		fileWriter.format("\t#call_function line:%d\n",line);


	}

	public void move_fp_to_sp(int line){
		fileWriter.format("\tmove $fp, $sp\t");
		fileWriter.format("\t#move_fp_to_sp line:%d\n",line);

	}
	public void move_fp_temp(TEMP temp, int line){
		fileWriter.format("\tmove $t%d, $fp\t", temp.serial);
		fileWriter.format("\t#move_fp_to_temp line:%d\n",line);
	}

	public void store_local_sp_offset(String register_name, int offset, int line){
		fileWriter.format("\tsw %s, %d($sp)\t", register_name, offset);
		fileWriter.format("\t#store_local_sp_offset line:%d\n",line);

	}
	public void store_local_fp_offset(String register_name, int offset, int line){
		fileWriter.format("\tsw %s, %d($fp)\t", register_name, offset);
		fileWriter.format("\t#store_local_fp_offset line:%d\n",line);

	}

	public void load_local_fp_offset(String register_name, int fp_offset, int line)
	{
		fileWriter.format("\tlw %s, %d($fp)\t", register_name, fp_offset);
		fileWriter.format("\t#load_local_fp_offset line:%d\n",line);

	}
	public void load_from_register(TEMP dst, TEMP src, int offset, int line)
	{
		fileWriter.format("\tlw $t%d, %d($t%d)\t", dst.serial, offset, src.serial);
		fileWriter.format("\t#load_from_register line:%d\n",line);

	}

	public void store_to_register(TEMP dst, TEMP src, int offset, int line)
	{
		fileWriter.format("\tsw $t%d, %d($t%d)\t", src.serial, offset, dst.serial);
		fileWriter.format("\t#store_to_register line:%d\n",line);

	}

	public void load_local_sp_offset(String register_name, int sp_offset, int line)
	{
		fileWriter.format("\tlw %s, %d($sp)\t", register_name, sp_offset);
		fileWriter.format("\t#load_local_sp_offset line:%d\n",line);

	}

	public void jump_register(String register_name, int line){
		fileWriter.format("\tjr %s\t", register_name);
		fileWriter.format("\t#jump_register line:%d\n",line);

	}
	public void jal_register(TEMP address, int line){
		String label= IRcommand.getFreshLabel("start");
		fileWriter.format("\tjal %s\n", label);
		label(label, line);
		fileWriter.format("\taddi $ra, 8\n");
		fileWriter.format("\tjr $t%d\t", address.serial);
		fileWriter.format("\t#jal_register line:%d\n",line);
	}

	public void li(TEMP t,int value, int line)
	{
		int idx=t.getSerialNumber();
		fileWriter.format("\tli $t%d,%d\t",idx,value);
		fileWriter.format("\t#li line:%d\n",line);

	}
	public void add(TEMP dst,TEMP oprnd1,TEMP oprnd2, int line)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tadd $t%d,$t%d,$t%d\t",dstidx,i1,i2);
		fileWriter.format("\t#add line:%d\n",line);

	}
	public void addi(TEMP dst,TEMP oprnd1,int oprnd2,int line)
	{
		int i1 =oprnd1.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\taddi $t%d,$t%d,%d\t",dstidx,i1,oprnd2);
		fileWriter.format("\t#addi line:%d\n",line);

	}
	public void sub(TEMP dst,TEMP oprnd1,TEMP oprnd2, int line)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tsub $t%d,$t%d,$t%d\t",dstidx,i1,i2);
		fileWriter.format("\t#sub line:%d\n",line);

	}
	public void mul(TEMP dst,TEMP oprnd1,TEMP oprnd2, int line)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tmul $t%d,$t%d,$t%d\t",dstidx,i1,i2);
		fileWriter.format("\t#mul line:%d\n",line);

	}
	
	public void div(TEMP dst,TEMP oprnd1,TEMP oprnd2, int line)  // first is divided by second
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tdiv $t%d,$t%d,$t%d\t",dstidx,i1,i2);
		fileWriter.format("\t#div line:%d\n",line);

	}
	
	public void label(String inlabel,int line)
	{
		fileWriter.format("%s:\t",inlabel);
		fileWriter.format("\t#label line:%d\n",line);
	}

	public void virtualTable_label(String inlabel)
	{
		fileWriter.format("%s:\t.word\t",inlabel);
	}
	public void virtualTable_entry_start(String entry)
	{
		fileWriter.format("%s",entry);
	}

	public void virtualTable_entry(String entry)
	{
		fileWriter.format(",\t%s",entry);
	}
	public void newline()
	{
		fileWriter.format("\n");
	}

	public void jump(String inlabel, int line)
	{
		fileWriter.format("\tj %s\t",inlabel);
		fileWriter.format("\t#jump line:%d\n",line);

	}	
	public void blt(TEMP oprnd1,TEMP oprnd2,String label,int line)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tblt $t%d,$t%d,%s\t",i1,i2,label);
		fileWriter.format("\t#blt line:%d\n",line);

	}
	public void bge(TEMP oprnd1,TEMP oprnd2,String label, int line)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbge $t%d,$t%d,%s\t",i1,i2,label);
		fileWriter.format("\t#bge line:%d\n",line);

	}
	public void bne(TEMP oprnd1,TEMP oprnd2,String label, int line)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbne $t%d,$t%d,%s\t",i1,i2,label);
		fileWriter.format("\t#bne line:%d\n",line);

	}
	public void beq(TEMP oprnd1,TEMP oprnd2,String label, int line)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();

		fileWriter.format("\tbeq $t%d,$t%d,%s\t",i1,i2,label);
		fileWriter.format("\t#beq line:%d\n",line);

	}
	public void beqz(TEMP oprnd1,String label, int line)
	{
		int i1 =oprnd1.getSerialNumber();
		fileWriter.format("\tbeq $t%d,$zero,%s\t",i1,label);
		fileWriter.format("\t#beqz line:%d\n",line);

	}
	
	public void bnez(TEMP oprnd1,String label, int line)
	{
		int i1 =oprnd1.getSerialNumber();
		fileWriter.format("\tbne $t%d,$zero,%s\t",i1,label);
		fileWriter.format("\t#bnez line:%d\n",line);

	}

	public void store_byte(TEMP src, TEMP addr, int offset, int line )
	{
		int i1 =src.getSerialNumber();
		int i2 =addr.getSerialNumber();
		fileWriter.format("\tsb $t%d,%d($t%d)\t",i1,offset,i2);
		fileWriter.format("\t#store_byte line:%d\n",line);
	}

	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static sir_MIPS_a_lot instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected sir_MIPS_a_lot() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static sir_MIPS_a_lot getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new sir_MIPS_a_lot();

			try
			{
				/*********************************************************************************/
				/* [1] Open the MIPS text file and write data section with error message strings */
				/*********************************************************************************/
				//String dirname="./FOLDER_5_OUTPUT/";
				//String filename="MIPS.s";
				String filename = GLOBAL_VARIABLES.OUTPUT_FILENAME;

				/***************************************/
				/* [2] Open MIPS text file for writing */
				/***************************************/
				instance.fileWriter = new PrintWriter(/*dirname+*/filename);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			/*****************************************************/
			/* [3] Print data section with error message strings */
			/*****************************************************/
			instance.fileWriter.print(".data\n");
			instance.fileWriter.print("string_access_violation: .asciiz \"Access Violation\"\n");
			instance.fileWriter.print("string_illegal_div_by_0: .asciiz \"Division By Zero\"\n");
			instance.fileWriter.print("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"\n");
			/*instance.fileWriter.print("new_line: .asciiz \\n");*/
		}
		return instance;
	}

	public void mallocate(TEMP oprnd1, TEMP oprnd2, TEMP dst, int line) {
		int i1 =oprnd1.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		if (oprnd2 != null) {
			int i2 =oprnd2.getSerialNumber();
			fileWriter.format("\tadd $a0,$t%d,$t%d\n", i1, i2);
		} else {
			fileWriter.format("\tadd $a0,$t%d,$zero\n",i1);
		}
		fileWriter.format("\tli $v0, 9\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tadd $t%d,$v0,$zero\t",dstidx);
		fileWriter.format("\t#mallocate line:%d\n",line);
	}

	public void set_to_zero(TEMP dst, int line){
		int dstidx=dst.getSerialNumber();
		fileWriter.format("\taddi $t%d,$zero,0\t",dstidx);
		fileWriter.format("\t#set_to_zero line:%d\n",line);
	}

	public void takeFromV0(TEMP dst,int line){
		int dstidx=dst.getSerialNumber();
		fileWriter.format("\tmove $t%d,$v0\t",dstidx);
		fileWriter.format("\t#takeFromV0 line:%d\n",line);
	}

	public void move(TEMP dst, TEMP src, int line){
		int dstidx=dst.getSerialNumber();
		int srcidx=src.getSerialNumber();
		fileWriter.format("\tmove $t%d,$t%d\t",dstidx,srcidx);
		fileWriter.format("\t#move line:%d\n",line);
	}
}
