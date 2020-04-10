.data
string_access_violation: .asciiz "Access Violation"
string_illegal_div_by_0: .asciiz "Illegal Division By Zero"
string_invalid_ptr_dref: .asciiz "Invalid Pointer Dereference"

	func_sum: .asciiz "sum"		#allocateFuncString line:7
	func_main: .asciiz "main"		#allocateFuncString line:25
.text
	li $t0,0		#li line:1
	sw $t0, -48($fp)		#store_local_fp_offset line:2
	li $t1,0		#li line:2
	sw $t1, -52($fp)		#store_local_fp_offset line:3
	li $t0,0		#li line:4
	sw $t0, -56($fp)		#store_local_fp_offset line:4
sum:		#label line:7
	addi $sp, $sp, -32		#change_stack line:7
	sw $t0, -4($fp)		#store_local_fp_offset line:7
	sw $t1, -8($fp)		#store_local_fp_offset line:7
	sw $t2, -12($fp)		#store_local_fp_offset line:7
	sw $t3, -16($fp)		#store_local_fp_offset line:7
	sw $t4, -20($fp)		#store_local_fp_offset line:7
	sw $t5, -24($fp)		#store_local_fp_offset line:7
	sw $t6, -28($fp)		#store_local_fp_offset line:7
	sw $t7, -32($fp)		#store_local_fp_offset line:7
	addi $sp, $sp, -100		#change_stack line:7
	lw $t0, 12($fp)		#load_local_fp_offset line:8
	lw $t0, 4($t0)		#load_from_register line:8
	sw $t0, -40($fp)		#store_local_fp_offset line:8
	lw $t0, 12($fp)		#load_local_fp_offset line:9
	lw $t0, 8($t0)		#load_from_register line:9
	sw $t0, -44($fp)		#store_local_fp_offset line:9
	lw $t0, 12($fp)		#load_local_fp_offset line:10
	lw $t0, 12($t0)		#load_from_register line:10
	sw $t0, -48($fp)		#store_local_fp_offset line:10
	lw $t0, -48($fp)		#load_local_fp_offset line:12
	sw $t0, -52($fp)		#store_local_fp_offset line:12
	li $t0,1		#li line:13
	lw $t2, -40($fp)		#load_local_fp_offset line:13
	li $t1,0		#li line:12
	addi $t2,$t2,0		#addi line:13
	addi $t1,$t1,0		#addi line:13
Label_1_LOOP:		#label line:13
	lb $t4,0($t2)		#load_byte line:13
	lb $t3,0($t1)		#load_byte line:13
	beq $t4,$t3,Label_41_AssignOne		#beq line:13
	bne $t4,$t3,Label_42_AssignZero		#bne line:13
Label_41_AssignOne:		#label line:13
	li $t3,1		#li line:13
	j Label_40_end		#jump line:13
Label_42_AssignZero:		#label line:13
	li $t3,0		#li line:13
	j Label_40_end		#jump line:13
Label_40_end:		#label line:13
	beq $t3,$zero,Label_2_NOT_EQUAL		#beqz line:13
	beq $t4,$zero,Label_3_EQUAL		#beqz line:13
	addi $t1,$t1,1		#addi line:13
	addi $t2,$t2,1		#addi line:13
	j Label_1_LOOP		#jump line:13
Label_3_EQUAL:		#label line:13
	li $t1,1		#li line:13
	j Label_4_END		#jump line:13
Label_2_NOT_EQUAL:		#label line:13
	li $t1,0		#li line:13
Label_4_END:		#label line:13
	sub $t2,$t0,$t1		#sub line:13
	li $t1,32768		#li line:13
	li $t0,-32769		#li line:13
	blt $t2,$t1,Label_44_AssignOne		#blt line:13
	bge $t2,$t1,Label_45_AssignZero		#bge line:13
Label_44_AssignOne:		#label line:13
	li $t1,1		#li line:13
	j Label_43_end		#jump line:13
Label_45_AssignZero:		#label line:13
	li $t1,0		#li line:13
	j Label_43_end		#jump line:13
Label_43_end:		#label line:13
	blt $t0,$t2,Label_47_AssignOne		#blt line:13
	bge $t0,$t2,Label_48_AssignZero		#bge line:13
Label_47_AssignOne:		#label line:13
	li $t0,1		#li line:13
	j Label_46_end		#jump line:13
Label_48_AssignZero:		#label line:13
	li $t0,0		#li line:13
	j Label_46_end		#jump line:13
Label_46_end:		#label line:13
	bne $t1,$zero,Label_5_CORRECT_UPPER_BOUND		#bnez line:13
	li $t2,32767		#li line:13
Label_5_CORRECT_UPPER_BOUND:		#label line:13
	bne $t0,$zero,Label_6_CORRECT_LOWER_BOUND		#bnez line:13
	li $t2,-32768		#li line:13
Label_6_CORRECT_LOWER_BOUND:		#label line:13
	beq $t2,$zero,Label_0_end		#beqz line:13
	lw $t1, -52($fp)		#load_local_fp_offset line:15
	addi $sp, $sp, -4		#change_stack line:15
	lw $t0, -40($fp)		#load_local_fp_offset line:15
	addi $sp, $sp, -4		#change_stack line:15
	sw $t0, 0($sp)		#store_local_sp_offset line:15
	la $t0,func_sum		#load_func_address line:15
	addi $sp, $sp, -4		#change_stack line:15
	sw $t0, 0($sp)		#store_local_sp_offset line:15
	addi $sp, $sp, -8		#change_stack line:15
	sw $fp, 4($sp)		#store_local_sp_offset line:15
	sw $ra, 0($sp)		#store_local_sp_offset line:15
	move $fp, $sp		#move_fp_to_sp line:15
	addi $sp, $sp, -100		#change_stack line:15
	jal sum		#call_function line:15
	addi $sp, $sp, 100		#change_stack line:15
	lw $fp, 4($sp)		#load_local_sp_offset line:15
	lw $ra, 0($sp)		#load_local_sp_offset line:15
	addi $sp, $sp, 16		#change_stack line:15
	lw $t0, 0($sp)		#load_local_sp_offset line:15
	addi $sp, $sp, 4		#change_stack line:15
	add $t2,$t1,$t0		#add line:15
	li $t1,32768		#li line:15
	li $t0,-32769		#li line:15
	blt $t2,$t1,Label_50_AssignOne		#blt line:15
	bge $t2,$t1,Label_51_AssignZero		#bge line:15
Label_50_AssignOne:		#label line:15
	li $t1,1		#li line:15
	j Label_49_end		#jump line:15
Label_51_AssignZero:		#label line:15
	li $t1,0		#li line:15
	j Label_49_end		#jump line:15
Label_49_end:		#label line:15
	blt $t0,$t2,Label_53_AssignOne		#blt line:15
	bge $t0,$t2,Label_54_AssignZero		#bge line:15
Label_53_AssignOne:		#label line:15
	li $t0,1		#li line:15
	j Label_52_end		#jump line:15
Label_54_AssignZero:		#label line:15
	li $t0,0		#li line:15
	j Label_52_end		#jump line:15
Label_52_end:		#label line:15
	bne $t1,$zero,Label_7_CORRECT_UPPER_BOUND		#bnez line:15
	li $t2,32767		#li line:15
Label_7_CORRECT_UPPER_BOUND:		#label line:15
	bne $t0,$zero,Label_8_CORRECT_LOWER_BOUND		#bnez line:15
	li $t2,-32768		#li line:15
Label_8_CORRECT_LOWER_BOUND:		#label line:15
	sw $t2, -52($fp)		#store_local_fp_offset line:15
Label_0_end:		#label line:13
	li $t0,1		#li line:17
	lw $t2, -44($fp)		#load_local_fp_offset line:17
	li $t1,0		#li line:16
	addi $t2,$t2,0		#addi line:17
	addi $t1,$t1,0		#addi line:17
Label_10_LOOP:		#label line:17
	lb $t4,0($t2)		#load_byte line:17
	lb $t3,0($t1)		#load_byte line:17
	beq $t4,$t3,Label_56_AssignOne		#beq line:17
	bne $t4,$t3,Label_57_AssignZero		#bne line:17
Label_56_AssignOne:		#label line:17
	li $t3,1		#li line:17
	j Label_55_end		#jump line:17
Label_57_AssignZero:		#label line:17
	li $t3,0		#li line:17
	j Label_55_end		#jump line:17
Label_55_end:		#label line:17
	beq $t3,$zero,Label_11_NOT_EQUAL		#beqz line:17
	beq $t4,$zero,Label_12_EQUAL		#beqz line:17
	addi $t1,$t1,1		#addi line:17
	addi $t2,$t2,1		#addi line:17
	j Label_10_LOOP		#jump line:17
Label_12_EQUAL:		#label line:17
	li $t1,1		#li line:17
	j Label_13_END		#jump line:17
Label_11_NOT_EQUAL:		#label line:17
	li $t1,0		#li line:17
Label_13_END:		#label line:17
	sub $t2,$t0,$t1		#sub line:17
	li $t1,32768		#li line:17
	li $t0,-32769		#li line:17
	blt $t2,$t1,Label_59_AssignOne		#blt line:17
	bge $t2,$t1,Label_60_AssignZero		#bge line:17
Label_59_AssignOne:		#label line:17
	li $t1,1		#li line:17
	j Label_58_end		#jump line:17
Label_60_AssignZero:		#label line:17
	li $t1,0		#li line:17
	j Label_58_end		#jump line:17
Label_58_end:		#label line:17
	blt $t0,$t2,Label_62_AssignOne		#blt line:17
	bge $t0,$t2,Label_63_AssignZero		#bge line:17
Label_62_AssignOne:		#label line:17
	li $t0,1		#li line:17
	j Label_61_end		#jump line:17
Label_63_AssignZero:		#label line:17
	li $t0,0		#li line:17
	j Label_61_end		#jump line:17
Label_61_end:		#label line:17
	bne $t1,$zero,Label_14_CORRECT_UPPER_BOUND		#bnez line:17
	li $t2,32767		#li line:17
Label_14_CORRECT_UPPER_BOUND:		#label line:17
	bne $t0,$zero,Label_15_CORRECT_LOWER_BOUND		#bnez line:17
	li $t2,-32768		#li line:17
Label_15_CORRECT_LOWER_BOUND:		#label line:17
	beq $t2,$zero,Label_9_end		#beqz line:17
	lw $t1, -52($fp)		#load_local_fp_offset line:19
	addi $sp, $sp, -4		#change_stack line:19
	lw $t0, -44($fp)		#load_local_fp_offset line:19
	addi $sp, $sp, -4		#change_stack line:19
	sw $t0, 0($sp)		#store_local_sp_offset line:19
	la $t0,func_sum		#load_func_address line:19
	addi $sp, $sp, -4		#change_stack line:19
	sw $t0, 0($sp)		#store_local_sp_offset line:19
	addi $sp, $sp, -8		#change_stack line:19
	sw $fp, 4($sp)		#store_local_sp_offset line:19
	sw $ra, 0($sp)		#store_local_sp_offset line:19
	move $fp, $sp		#move_fp_to_sp line:19
	addi $sp, $sp, -100		#change_stack line:19
	jal sum		#call_function line:19
	addi $sp, $sp, 100		#change_stack line:19
	lw $fp, 4($sp)		#load_local_sp_offset line:19
	lw $ra, 0($sp)		#load_local_sp_offset line:19
	addi $sp, $sp, 16		#change_stack line:19
	lw $t0, 0($sp)		#load_local_sp_offset line:19
	addi $sp, $sp, 4		#change_stack line:19
	add $t2,$t1,$t0		#add line:19
	li $t1,32768		#li line:19
	li $t0,-32769		#li line:19
	blt $t2,$t1,Label_65_AssignOne		#blt line:19
	bge $t2,$t1,Label_66_AssignZero		#bge line:19
Label_65_AssignOne:		#label line:19
	li $t1,1		#li line:19
	j Label_64_end		#jump line:19
Label_66_AssignZero:		#label line:19
	li $t1,0		#li line:19
	j Label_64_end		#jump line:19
Label_64_end:		#label line:19
	blt $t0,$t2,Label_68_AssignOne		#blt line:19
	bge $t0,$t2,Label_69_AssignZero		#bge line:19
Label_68_AssignOne:		#label line:19
	li $t0,1		#li line:19
	j Label_67_end		#jump line:19
Label_69_AssignZero:		#label line:19
	li $t0,0		#li line:19
	j Label_67_end		#jump line:19
Label_67_end:		#label line:19
	bne $t1,$zero,Label_16_CORRECT_UPPER_BOUND		#bnez line:19
	li $t2,32767		#li line:19
Label_16_CORRECT_UPPER_BOUND:		#label line:19
	bne $t0,$zero,Label_17_CORRECT_LOWER_BOUND		#bnez line:19
	li $t2,-32768		#li line:19
Label_17_CORRECT_LOWER_BOUND:		#label line:19
	sw $t2, -52($fp)		#store_local_fp_offset line:19
Label_9_end:		#label line:17
	lw $t0, -52($fp)		#load_local_fp_offset line:21
	sw $t0, 16($fp)		#store_local_fp_offset line:21
	lw $t0, -4($fp)		#load_local_fp_offset line:21
	lw $t1, -8($fp)		#load_local_fp_offset line:21
	lw $t2, -12($fp)		#load_local_fp_offset line:21
	lw $t3, -16($fp)		#load_local_fp_offset line:21
	lw $t4, -20($fp)		#load_local_fp_offset line:21
	lw $t5, -24($fp)		#load_local_fp_offset line:21
	lw $t6, -28($fp)		#load_local_fp_offset line:21
	lw $t7, -32($fp)		#load_local_fp_offset line:21
	addi $sp, $sp, 100		#change_stack line:21
	addi $sp, $sp, 32		#change_stack line:21
	jr $ra		#jump_register line:21
main:		#label line:25
	la $t0,func_main		#load_func_address line:25
	addi $sp, $sp, -12		#change_stack line:25
	sw $t0, 8($sp)		#store_local_sp_offset line:25
	sw $zero, 4($sp)		#store_local_sp_offset line:25
	move $fp, $sp		#move_fp_to_sp line:25
	addi $sp, $sp, -32		#change_stack line:25
	sw $t0, -4($fp)		#store_local_fp_offset line:25
	sw $t1, -8($fp)		#store_local_fp_offset line:25
	sw $t2, -12($fp)		#store_local_fp_offset line:25
	sw $t3, -16($fp)		#store_local_fp_offset line:25
	sw $t4, -20($fp)		#store_local_fp_offset line:25
	sw $t5, -24($fp)		#store_local_fp_offset line:25
	sw $t6, -28($fp)		#store_local_fp_offset line:25
	sw $t7, -32($fp)		#store_local_fp_offset line:25
	addi $sp, $sp, -100		#change_stack line:25
	li $t0,16		#li line:27
	add $a0,$t0,$zero
	li $v0, 9
	syscall
	add $t1,$v0,$zero		#mallocate line:27
	li $t0,0		#li line:1
	sw $t0, 4($t1)		#store_to_register line:27
	li $t0,0		#li line:2
	sw $t0, 8($t1)		#store_to_register line:27
	li $t0,0		#li line:4
	sw $t0, 12($t1)		#store_to_register line:27
	sw $t1, -40($fp)		#store_local_fp_offset line:27
	li $t2,17		#li line:28
	addi $t0,$zero,0		#set_to_zero line:28
	addi $t0,$t0,4		#addi line:28
	addi $t1,$t2,0		#addi line:28
	mul $t1,$t1,$t0		#mul line:28
	addi $t1,$t1,4		#addi line:28
	add $a0,$t1,$zero
	li $v0, 9
	syscall
	add $t1,$v0,$zero		#mallocate line:28
	addi $t0,$t2,-1		#addi line:28
	sw $t0, 0($t1)		#store_to_register line:28
	sw $t1, -44($fp)		#store_local_fp_offset line:28
	li $t0,0		#li line:30
	sw $t0, -48($fp)		#store_local_fp_offset line:30
Label_19_start:		#label line:32
	lw $t1, -48($fp)		#load_local_fp_offset line:32
	li $t0,17		#li line:32
	blt $t1,$t0,Label_71_AssignOne		#blt line:32
	bge $t1,$t0,Label_72_AssignZero		#bge line:32
Label_71_AssignOne:		#label line:32
	li $t0,1		#li line:32
	j Label_70_end		#jump line:32
Label_72_AssignZero:		#label line:32
	li $t0,0		#li line:32
	j Label_70_end		#jump line:32
Label_70_end:		#label line:32
	beq $t0,$zero,Label_18_end		#beqz line:32
	lw $t1, -44($fp)		#load_local_fp_offset line:34
	li $t0,16		#li line:34
	add $a0,$t0,$zero
	li $v0, 9
	syscall
	add $t0,$v0,$zero		#mallocate line:34
	li $t2,0		#li line:1
	sw $t2, 4($t0)		#store_to_register line:34
	li $t2,0		#li line:2
	sw $t2, 8($t0)		#store_to_register line:34
	li $t2,0		#li line:4
	sw $t2, 12($t0)		#store_to_register line:34
	lw $t3, -48($fp)		#load_local_fp_offset line:34
	lw $t2, 0($t1)		#load_from_register line:34
	bge $t2,$t3,Label_20_VALID_ACCESS		#bge line:34
	la $t2,string_access_violation		#load_string_address line:34
	#print_string line:34
	move $a0,$t2
	li $v0,4
	syscall
	li $v0,10		#exit line:34
	syscall
Label_20_VALID_ACCESS:		#label line:34
	add $t1,$t1,$t3		#add line:34
	add $t1,$t1,$t3		#add line:34
	add $t1,$t1,$t3		#add line:34
	add $t1,$t1,$t3		#add line:34
	addi $t1,$t1,4		#addi line:34
	sw $t0, 0($t1)		#store_to_register line:34
	lw $t2, -44($fp)		#load_local_fp_offset line:35
	lw $t1, -48($fp)		#load_local_fp_offset line:35
	lw $t0, 0($t2)		#load_from_register line:35
	bge $t0,$t1,Label_21_VALID_ACCESS		#bge line:35
	la $t0,string_access_violation		#load_string_address line:35
	#print_string line:35
	move $a0,$t0
	li $v0,4
	syscall
	li $v0,10		#exit line:35
	syscall
Label_21_VALID_ACCESS:		#label line:35
	add $t0,$t2,$t1		#add line:35
	add $t0,$t0,$t1		#add line:35
	add $t0,$t0,$t1		#add line:35
	add $t0,$t0,$t1		#add line:35
	addi $t0,$t0,4		#addi line:35
	lw $t3, 0($t0)		#load_from_register line:35
	li $t1,19		#li line:35
	lw $t0, -48($fp)		#load_local_fp_offset line:35
	mul $t2,$t1,$t0		#mul line:35
	li $t1,32768		#li line:35
	li $t0,-32769		#li line:35
	blt $t2,$t1,Label_74_AssignOne		#blt line:35
	bge $t2,$t1,Label_75_AssignZero		#bge line:35
Label_74_AssignOne:		#label line:35
	li $t1,1		#li line:35
	j Label_73_end		#jump line:35
Label_75_AssignZero:		#label line:35
	li $t1,0		#li line:35
	j Label_73_end		#jump line:35
Label_73_end:		#label line:35
	blt $t0,$t2,Label_77_AssignOne		#blt line:35
	bge $t0,$t2,Label_78_AssignZero		#bge line:35
Label_77_AssignOne:		#label line:35
	li $t0,1		#li line:35
	j Label_76_end		#jump line:35
Label_78_AssignZero:		#label line:35
	li $t0,0		#li line:35
	j Label_76_end		#jump line:35
Label_76_end:		#label line:35
	bne $t1,$zero,Label_22_CORRECT_UPPER_BOUND		#bnez line:35
	li $t2,32767		#li line:35
Label_22_CORRECT_UPPER_BOUND:		#label line:35
	bne $t0,$zero,Label_23_CORRECT_LOWER_BOUND		#bnez line:35
	li $t2,-32768		#li line:35
Label_23_CORRECT_LOWER_BOUND:		#label line:35
	sw $t2, 12($t3)		#store_to_register line:35
	lw $t1, -48($fp)		#load_local_fp_offset line:36
	li $t0,1		#li line:36
	add $t2,$t1,$t0		#add line:36
	li $t1,32768		#li line:36
	li $t0,-32769		#li line:36
	blt $t2,$t1,Label_80_AssignOne		#blt line:36
	bge $t2,$t1,Label_81_AssignZero		#bge line:36
Label_80_AssignOne:		#label line:36
	li $t1,1		#li line:36
	j Label_79_end		#jump line:36
Label_81_AssignZero:		#label line:36
	li $t1,0		#li line:36
	j Label_79_end		#jump line:36
Label_79_end:		#label line:36
	blt $t0,$t2,Label_83_AssignOne		#blt line:36
	bge $t0,$t2,Label_84_AssignZero		#bge line:36
Label_83_AssignOne:		#label line:36
	li $t0,1		#li line:36
	j Label_82_end		#jump line:36
Label_84_AssignZero:		#label line:36
	li $t0,0		#li line:36
	j Label_82_end		#jump line:36
Label_82_end:		#label line:36
	bne $t1,$zero,Label_24_CORRECT_UPPER_BOUND		#bnez line:36
	li $t2,32767		#li line:36
Label_24_CORRECT_UPPER_BOUND:		#label line:36
	bne $t0,$zero,Label_25_CORRECT_LOWER_BOUND		#bnez line:36
	li $t2,-32768		#li line:36
Label_25_CORRECT_LOWER_BOUND:		#label line:36
	sw $t2, -48($fp)		#store_local_fp_offset line:36
	j Label_19_start		#jump line:32
Label_18_end:		#label line:32
	lw $t3, -40($fp)		#load_local_fp_offset line:39
	lw $t2, -44($fp)		#load_local_fp_offset line:39
	li $t1,0		#li line:39
	lw $t0, 0($t2)		#load_from_register line:39
	bge $t0,$t1,Label_26_VALID_ACCESS		#bge line:39
	la $t0,string_access_violation		#load_string_address line:39
	#print_string line:39
	move $a0,$t0
	li $v0,4
	syscall
	li $v0,10		#exit line:39
	syscall
Label_26_VALID_ACCESS:		#label line:39
	add $t0,$t2,$t1		#add line:39
	add $t0,$t0,$t1		#add line:39
	add $t0,$t0,$t1		#add line:39
	add $t0,$t0,$t1		#add line:39
	addi $t0,$t0,4		#addi line:39
	lw $t0, 0($t0)		#load_from_register line:39
	sw $t0, 4($t3)		#store_to_register line:39
	lw $t3, -40($fp)		#load_local_fp_offset line:40
	lw $t2, -44($fp)		#load_local_fp_offset line:40
	li $t1,1		#li line:40
	lw $t0, 0($t2)		#load_from_register line:40
	bge $t0,$t1,Label_27_VALID_ACCESS		#bge line:40
	la $t0,string_access_violation		#load_string_address line:40
	#print_string line:40
	move $a0,$t0
	li $v0,4
	syscall
	li $v0,10		#exit line:40
	syscall
Label_27_VALID_ACCESS:		#label line:40
	add $t0,$t2,$t1		#add line:40
	add $t0,$t0,$t1		#add line:40
	add $t0,$t0,$t1		#add line:40
	add $t0,$t0,$t1		#add line:40
	addi $t0,$t0,4		#addi line:40
	lw $t0, 0($t0)		#load_from_register line:40
	sw $t0, 8($t3)		#store_to_register line:40
	lw $t0, -40($fp)		#load_local_fp_offset line:42
	lw $t3, 4($t0)		#load_from_register line:42
	lw $t2, -44($fp)		#load_local_fp_offset line:42
	li $t1,2		#li line:42
	lw $t0, 0($t2)		#load_from_register line:42
	bge $t0,$t1,Label_28_VALID_ACCESS		#bge line:42
	la $t0,string_access_violation		#load_string_address line:42
	#print_string line:42
	move $a0,$t0
	li $v0,4
	syscall
	li $v0,10		#exit line:42
	syscall
Label_28_VALID_ACCESS:		#label line:42
	add $t0,$t2,$t1		#add line:42
	add $t0,$t0,$t1		#add line:42
	add $t0,$t0,$t1		#add line:42
	add $t0,$t0,$t1		#add line:42
	addi $t0,$t0,4		#addi line:42
	lw $t0, 0($t0)		#load_from_register line:42
	sw $t0, 4($t3)		#store_to_register line:42
	lw $t0, -40($fp)		#load_local_fp_offset line:43
	lw $t3, 4($t0)		#load_from_register line:43
	lw $t2, -44($fp)		#load_local_fp_offset line:43
	li $t1,3		#li line:43
	lw $t0, 0($t2)		#load_from_register line:43
	bge $t0,$t1,Label_29_VALID_ACCESS		#bge line:43
	la $t0,string_access_violation		#load_string_address line:43
	#print_string line:43
	move $a0,$t0
	li $v0,4
	syscall
	li $v0,10		#exit line:43
	syscall
Label_29_VALID_ACCESS:		#label line:43
	add $t0,$t2,$t1		#add line:43
	add $t0,$t0,$t1		#add line:43
	add $t0,$t0,$t1		#add line:43
	add $t0,$t0,$t1		#add line:43
	addi $t0,$t0,4		#addi line:43
	lw $t0, 0($t0)		#load_from_register line:43
	sw $t0, 8($t3)		#store_to_register line:43
	lw $t0, -40($fp)		#load_local_fp_offset line:44
	lw $t3, 8($t0)		#load_from_register line:44
	lw $t2, -44($fp)		#load_local_fp_offset line:44
	li $t1,4		#li line:44
	lw $t0, 0($t2)		#load_from_register line:44
	bge $t0,$t1,Label_30_VALID_ACCESS		#bge line:44
	la $t0,string_access_violation		#load_string_address line:44
	#print_string line:44
	move $a0,$t0
	li $v0,4
	syscall
	li $v0,10		#exit line:44
	syscall
Label_30_VALID_ACCESS:		#label line:44
	add $t0,$t2,$t1		#add line:44
	add $t0,$t0,$t1		#add line:44
	add $t0,$t0,$t1		#add line:44
	add $t0,$t0,$t1		#add line:44
	addi $t0,$t0,4		#addi line:44
	lw $t0, 0($t0)		#load_from_register line:44
	sw $t0, 4($t3)		#store_to_register line:44
	lw $t0, -40($fp)		#load_local_fp_offset line:45
	lw $t3, 8($t0)		#load_from_register line:45
	lw $t2, -44($fp)		#load_local_fp_offset line:45
	li $t1,5		#li line:45
	lw $t0, 0($t2)		#load_from_register line:45
	bge $t0,$t1,Label_31_VALID_ACCESS		#bge line:45
	la $t0,string_access_violation		#load_string_address line:45
	#print_string line:45
	move $a0,$t0
	li $v0,4
	syscall
	li $v0,10		#exit line:45
	syscall
Label_31_VALID_ACCESS:		#label line:45
	add $t0,$t2,$t1		#add line:45
	add $t0,$t0,$t1		#add line:45
	add $t0,$t0,$t1		#add line:45
	add $t0,$t0,$t1		#add line:45
	addi $t0,$t0,4		#addi line:45
	lw $t0, 0($t0)		#load_from_register line:45
	sw $t0, 8($t3)		#store_to_register line:45
	lw $t0, -40($fp)		#load_local_fp_offset line:47
	lw $t0, 4($t0)		#load_from_register line:47
	lw $t3, 4($t0)		#load_from_register line:47
	lw $t2, -44($fp)		#load_local_fp_offset line:47
	li $t1,6		#li line:47
	lw $t0, 0($t2)		#load_from_register line:47
	bge $t0,$t1,Label_32_VALID_ACCESS		#bge line:47
	la $t0,string_access_violation		#load_string_address line:47
	#print_string line:47
	move $a0,$t0
	li $v0,4
	syscall
	li $v0,10		#exit line:47
	syscall
Label_32_VALID_ACCESS:		#label line:47
	add $t0,$t2,$t1		#add line:47
	add $t0,$t0,$t1		#add line:47
	add $t0,$t0,$t1		#add line:47
	add $t0,$t0,$t1		#add line:47
	addi $t0,$t0,4		#addi line:47
	lw $t0, 0($t0)		#load_from_register line:47
	sw $t0, 4($t3)		#store_to_register line:47
	lw $t0, -40($fp)		#load_local_fp_offset line:48
	lw $t0, 4($t0)		#load_from_register line:48
	lw $t3, 4($t0)		#load_from_register line:48
	lw $t2, -44($fp)		#load_local_fp_offset line:48
	li $t1,7		#li line:48
	lw $t0, 0($t2)		#load_from_register line:48
	bge $t0,$t1,Label_33_VALID_ACCESS		#bge line:48
	la $t0,string_access_violation		#load_string_address line:48
	#print_string line:48
	move $a0,$t0
	li $v0,4
	syscall
	li $v0,10		#exit line:48
	syscall
Label_33_VALID_ACCESS:		#label line:48
	add $t0,$t2,$t1		#add line:48
	add $t0,$t0,$t1		#add line:48
	add $t0,$t0,$t1		#add line:48
	add $t0,$t0,$t1		#add line:48
	addi $t0,$t0,4		#addi line:48
	lw $t0, 0($t0)		#load_from_register line:48
	sw $t0, 8($t3)		#store_to_register line:48
	lw $t0, -40($fp)		#load_local_fp_offset line:49
	lw $t0, 4($t0)		#load_from_register line:49
	lw $t3, 8($t0)		#load_from_register line:49
	lw $t2, -44($fp)		#load_local_fp_offset line:49
	li $t1,8		#li line:49
	lw $t0, 0($t2)		#load_from_register line:49
	bge $t0,$t1,Label_34_VALID_ACCESS		#bge line:49
	la $t0,string_access_violation		#load_string_address line:49
	#print_string line:49
	move $a0,$t0
	li $v0,4
	syscall
	li $v0,10		#exit line:49
	syscall
Label_34_VALID_ACCESS:		#label line:49
	add $t0,$t2,$t1		#add line:49
	add $t0,$t0,$t1		#add line:49
	add $t0,$t0,$t1		#add line:49
	add $t0,$t0,$t1		#add line:49
	addi $t0,$t0,4		#addi line:49
	lw $t0, 0($t0)		#load_from_register line:49
	sw $t0, 4($t3)		#store_to_register line:49
	lw $t0, -40($fp)		#load_local_fp_offset line:50
	lw $t0, 4($t0)		#load_from_register line:50
	lw $t3, 8($t0)		#load_from_register line:50
	lw $t2, -44($fp)		#load_local_fp_offset line:50
	li $t1,9		#li line:50
	lw $t0, 0($t2)		#load_from_register line:50
	bge $t0,$t1,Label_35_VALID_ACCESS		#bge line:50
	la $t0,string_access_violation		#load_string_address line:50
	#print_string line:50
	move $a0,$t0
	li $v0,4
	syscall
	li $v0,10		#exit line:50
	syscall
Label_35_VALID_ACCESS:		#label line:50
	add $t0,$t2,$t1		#add line:50
	add $t0,$t0,$t1		#add line:50
	add $t0,$t0,$t1		#add line:50
	add $t0,$t0,$t1		#add line:50
	addi $t0,$t0,4		#addi line:50
	lw $t0, 0($t0)		#load_from_register line:50
	sw $t0, 8($t3)		#store_to_register line:50
	lw $t0, -40($fp)		#load_local_fp_offset line:51
	lw $t0, 8($t0)		#load_from_register line:51
	lw $t3, 4($t0)		#load_from_register line:51
	lw $t2, -44($fp)		#load_local_fp_offset line:51
	li $t1,10		#li line:51
	lw $t0, 0($t2)		#load_from_register line:51
	bge $t0,$t1,Label_36_VALID_ACCESS		#bge line:51
	la $t0,string_access_violation		#load_string_address line:51
	#print_string line:51
	move $a0,$t0
	li $v0,4
	syscall
	li $v0,10		#exit line:51
	syscall
Label_36_VALID_ACCESS:		#label line:51
	add $t0,$t2,$t1		#add line:51
	add $t0,$t0,$t1		#add line:51
	add $t0,$t0,$t1		#add line:51
	add $t0,$t0,$t1		#add line:51
	addi $t0,$t0,4		#addi line:51
	lw $t0, 0($t0)		#load_from_register line:51
	sw $t0, 4($t3)		#store_to_register line:51
	lw $t0, -40($fp)		#load_local_fp_offset line:52
	lw $t0, 8($t0)		#load_from_register line:52
	lw $t3, 4($t0)		#load_from_register line:52
	lw $t2, -44($fp)		#load_local_fp_offset line:52
	li $t1,11		#li line:52
	lw $t0, 0($t2)		#load_from_register line:52
	bge $t0,$t1,Label_37_VALID_ACCESS		#bge line:52
	la $t0,string_access_violation		#load_string_address line:52
	#print_string line:52
	move $a0,$t0
	li $v0,4
	syscall
	li $v0,10		#exit line:52
	syscall
Label_37_VALID_ACCESS:		#label line:52
	add $t0,$t2,$t1		#add line:52
	add $t0,$t0,$t1		#add line:52
	add $t0,$t0,$t1		#add line:52
	add $t0,$t0,$t1		#add line:52
	addi $t0,$t0,4		#addi line:52
	lw $t0, 0($t0)		#load_from_register line:52
	sw $t0, 8($t3)		#store_to_register line:52
	lw $t0, -40($fp)		#load_local_fp_offset line:53
	lw $t0, 8($t0)		#load_from_register line:53
	lw $t3, 8($t0)		#load_from_register line:53
	lw $t2, -44($fp)		#load_local_fp_offset line:53
	li $t1,12		#li line:53
	lw $t0, 0($t2)		#load_from_register line:53
	bge $t0,$t1,Label_38_VALID_ACCESS		#bge line:53
	la $t0,string_access_violation		#load_string_address line:53
	#print_string line:53
	move $a0,$t0
	li $v0,4
	syscall
	li $v0,10		#exit line:53
	syscall
Label_38_VALID_ACCESS:		#label line:53
	add $t0,$t2,$t1		#add line:53
	add $t0,$t0,$t1		#add line:53
	add $t0,$t0,$t1		#add line:53
	add $t0,$t0,$t1		#add line:53
	addi $t0,$t0,4		#addi line:53
	lw $t0, 0($t0)		#load_from_register line:53
	sw $t0, 4($t3)		#store_to_register line:53
	lw $t0, -40($fp)		#load_local_fp_offset line:54
	lw $t0, 8($t0)		#load_from_register line:54
	lw $t3, 8($t0)		#load_from_register line:54
	lw $t2, -44($fp)		#load_local_fp_offset line:54
	li $t1,13		#li line:54
	lw $t0, 0($t2)		#load_from_register line:54
	bge $t0,$t1,Label_39_VALID_ACCESS		#bge line:54
	la $t0,string_access_violation		#load_string_address line:54
	#print_string line:54
	move $a0,$t0
	li $v0,4
	syscall
	li $v0,10		#exit line:54
	syscall
Label_39_VALID_ACCESS:		#label line:54
	add $t0,$t2,$t1		#add line:54
	add $t0,$t0,$t1		#add line:54
	add $t0,$t0,$t1		#add line:54
	add $t0,$t0,$t1		#add line:54
	addi $t0,$t0,4		#addi line:54
	lw $t0, 0($t0)		#load_from_register line:54
	sw $t0, 8($t3)		#store_to_register line:54
	addi $sp, $sp, -4		#change_stack line:56
	lw $t0, -40($fp)		#load_local_fp_offset line:56
	addi $sp, $sp, -4		#change_stack line:56
	sw $t0, 0($sp)		#store_local_sp_offset line:56
	la $t0,func_sum		#load_func_address line:56
	addi $sp, $sp, -4		#change_stack line:56
	sw $t0, 0($sp)		#store_local_sp_offset line:56
	addi $sp, $sp, -8		#change_stack line:56
	sw $fp, 4($sp)		#store_local_sp_offset line:56
	sw $ra, 0($sp)		#store_local_sp_offset line:56
	move $fp, $sp		#move_fp_to_sp line:56
	addi $sp, $sp, -100		#change_stack line:56
	jal sum		#call_function line:56
	addi $sp, $sp, 100		#change_stack line:56
	lw $fp, 4($sp)		#load_local_sp_offset line:56
	lw $ra, 0($sp)		#load_local_sp_offset line:56
	addi $sp, $sp, 16		#change_stack line:56
	lw $t0, 0($sp)		#load_local_sp_offset line:56
	addi $sp, $sp, 4		#change_stack line:56
	#print_int line:56
	move $a0,$t0
	li $v0,1
	syscall
	li $a0,32
	li $v0,11
	syscall
	lw $t0, -4($fp)		#load_local_fp_offset line:-1
	lw $t1, -8($fp)		#load_local_fp_offset line:-1
	lw $t2, -12($fp)		#load_local_fp_offset line:-1
	lw $t3, -16($fp)		#load_local_fp_offset line:-1
	lw $t4, -20($fp)		#load_local_fp_offset line:-1
	lw $t5, -24($fp)		#load_local_fp_offset line:-1
	lw $t6, -28($fp)		#load_local_fp_offset line:-1
	lw $t7, -32($fp)		#load_local_fp_offset line:-1
	addi $sp, $sp, 100		#change_stack line:-1
	addi $sp, $sp, 32		#change_stack line:-1
	li $v0,10		#exit line:-1
	syscall
