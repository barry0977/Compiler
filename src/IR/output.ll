declare void @print(ptr, ptr, i32, i32)
declare void @println()
declare void @printInt()
declare void @printlnInt()
declare ptr @getString()
declare i32 @getInt()
declare ptr @toString(i32)
declare i32 @string.length(ptr)
declare ptr @string.substring(ptr, i32, i32)
declare i32 @string.parseInt(ptr)
declare i32 @string.ord(ptr, i32)
declare ptr @string.add(ptr, ptr)
declare i1 @string.equal(ptr, ptr)
declare i1 @string.notEqual(ptr, ptr)
declare i1 @string.less(ptr, ptr)
declare i1 @string.lessOrEqual(ptr, ptr)
declare i1 @string.greater(ptr, ptr)
declare i1 @string.greaterOrEqual(ptr, ptr)
declare i32 @array.size(ptr)
declare ptr @_malloc(i32)
declare ptr @_malloc_array(i32)
%class.TA = type {ptr, i32 }
@init_anger = global i32 100
@work_anger = global i32 10
@.str.0 = private unnamed_addr constant [3 x i8] c", \00"
@.str.1 = private unnamed_addr constant [22 x i8] c" enjoys this work. XD\00"
@.str.2 = private unnamed_addr constant [3 x i8] c", \00"
@.str.3 = private unnamed_addr constant [23 x i8] c" wants to give up!!!!!\00"
@.str.4 = private unnamed_addr constant [15 x i8] c"the leading TA\00"
@.str.5 = private unnamed_addr constant [16 x i8] c"the striking TA\00"
@.str.6 = private unnamed_addr constant [3 x i8] c"MR\00"
@.str.7 = private unnamed_addr constant [5 x i8] c"Mars\00"
@.str.8 = private unnamed_addr constant [5 x i8] c"Mars\00"
define void @work(ptr %st, ptr %ta) {
entry:
	%st.1.2 = alloca ptr;
	store ptr %st, ptr %st.1.2;
	%ta.1.2 = alloca ptr;
	store ptr %ta, ptr %ta.1.2;
	%0 = load ptr, ptr %ta.1.2;
	%1 = getelementptr %class.TA, ptr %0, i32 0, i32 1;
	%2 = load i32, ptr %1;
	%3 = icmp sle i32 %2, 100;
	br i1 %3, label %if.then.2.1,label %if.else.2.1;
if.then.2.1:
	%4 = load ptr, ptr %st.1.2;
	%5 = call ptr @string.add(ptr %4, ptr @.str.0)
	%6 = load ptr, ptr %ta.1.2;
	%7 = getelementptr %class.TA, ptr %6, i32 0, i32 0;
	%8 = load ptr, ptr %7;
	%9 = call ptr @string.add(ptr %5, ptr %8)
	%10 = call ptr @string.add(ptr %9, ptr @.str.1)
	call void @println(ptr %10)
	br label %if.end.2.1;
if.else.2.1:
	%11 = load ptr, ptr %st.1.2;
	%12 = call ptr @string.add(ptr %11, ptr @.str.2)
	%13 = load ptr, ptr %ta.1.2;
	%14 = getelementptr %class.TA, ptr %13, i32 0, i32 0;
	%15 = load ptr, ptr %14;
	%16 = call ptr @string.add(ptr %12, ptr %15)
	%17 = call ptr @string.add(ptr %16, ptr @.str.3)
	call void @println(ptr %17)
	br label %if.end.2.1;
if.end.2.1:
	%18 = load ptr, ptr %ta.1.2;
	%19 = getelementptr %class.TA, ptr %18, i32 0, i32 1;
	%20 = load i32, ptr %19;
	%21 = load ptr, ptr %ta.1.2;
	%22 = getelementptr %class.TA, ptr %21, i32 0, i32 1;
	%23 = load i32, ptr %22;
	%24 = load i32, ptr @work_anger;
	%25 = add i32 %23, %24
	store i32 %25, ptr %19;
	ret i32 0;
}

define i32 @main() {
entry:
	%mr.1.3 = alloca ptr;
	%mars.1.3 = alloca ptr;
	%0 = load ptr, ptr %mr.1.3;
	%1 = call ptr @_malloc(i32 2)
	store ptr %1, ptr %mr.1.3;
	%2 = load ptr, ptr %mr.1.3;
	%3 = getelementptr %class.TA, ptr %2, i32 0, i32 0;
	%4 = load ptr, ptr %3;
	store ptr @.str.4, ptr %3;
	%5 = load ptr, ptr %mr.1.3;
	%6 = getelementptr %class.TA, ptr %5, i32 0, i32 1;
	%7 = load i32, ptr %6;
	store i32 0, ptr %6;
	%8 = load ptr, ptr %mars.1.3;
	%9 = call ptr @_malloc(i32 2)
	store ptr %9, ptr %mars.1.3;
	%10 = load ptr, ptr %mars.1.3;
	%11 = getelementptr %class.TA, ptr %10, i32 0, i32 0;
	%12 = load ptr, ptr %11;
	store ptr @.str.5, ptr %11;
	%13 = load ptr, ptr %mars.1.3;
	%14 = getelementptr %class.TA, ptr %13, i32 0, i32 1;
	%15 = load i32, ptr %14;
	%16 = load i32, ptr @init_anger;
	store i32 %16, ptr %14;
	%17 = load ptr, ptr %mr.1.3;
	call void @work(ptr @.str.6, ptr %17)
	%18 = load ptr, ptr %mars.1.3;
	call void @work(ptr @.str.7, ptr %18)
	%19 = load ptr, ptr %mars.1.3;
	call void @work(ptr @.str.8, ptr %19)
	ret i32 0;
}

