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
define i32 @main() {
entry:
	%a.1.1 = alloca ptr;
	%0 = call ptr @_malloc_array(i32 100)
	store ptr %0, ptr %a.1.1;
	%i.1.1 = alloca i32;
	%j.1.1 = alloca i32;
	%1 = load i32, ptr %i.1.1;
	store i32 0, ptr %i.1.1;
	%sum.1.1 = alloca i32;
	%quotient.1.1 = alloca i32;
	%remainder.1.1 = alloca i32;
	br label %for.cond.0;
for.cond.0:
	%2 = load i32, ptr %i.1.1;
	%3 = icmp slt i32 %2, 100;
	br i1 %3, label %for.body.0,label %for.end.0;
for.body.0:
	%4 = load ptr, ptr %a.1.1;
	%5 = load i32, ptr %i.1.1;
	%6 = getelementptr ptr, ptr %4, i32 %5;
	%7 = load ptr, ptr %6;
	%8 = call ptr @_malloc_array(i32 100)
	store ptr %8, ptr %6;
	br label %for.step.0;
for.step.0:
	%9 = load i32, ptr %i.1.1;
	%10 = add i32 %9, 1
	store i32 %10, ptr %i.1.1;
	br label %for.cond.0;
for.end.0:
	store i32 0, ptr %sum.1.1;
	%11 = load i32, ptr %i.1.1;
	store i32 0, ptr %i.1.1;
	br label %for.cond.1;
for.cond.1:
	%12 = load i32, ptr %i.1.1;
	%13 = icmp slt i32 %12, 100;
	br i1 %13, label %for.body.1,label %for.end.1;
for.body.1:
	%14 = load i32, ptr %j.1.1;
	store i32 0, ptr %j.1.1;
	br label %for.cond.2;
for.cond.2:
	%15 = load i32, ptr %j.1.1;
	%16 = icmp slt i32 %15, 100;
	br i1 %16, label %for.body.2,label %for.end.2;
for.body.2:
	%17 = load ptr, ptr %a.1.1;
	%18 = load i32, ptr %i.1.1;
	%19 = getelementptr ptr, ptr %17, i32 %18;
	%20 = load ptr, ptr %19;
	%21 = load i32, ptr %j.1.1;
	%22 = getelementptr ptr, ptr %20, i32 %21;
	%23 = load i32, ptr %22;
	store i32 0, ptr %22;
	br label %for.step.2;
for.step.2:
	%24 = load i32, ptr %j.1.1;
	%25 = add i32 %24, 1
	store i32 %25, ptr %j.1.1;
	br label %for.cond.2;
for.end.2:
	br label %for.step.1;
for.step.1:
	%26 = load i32, ptr %i.1.1;
	%27 = add i32 %26, 1
	store i32 %27, ptr %i.1.1;
	br label %for.cond.1;
for.end.1:
	%28 = load i32, ptr %i.1.1;
	store i32 0, ptr %i.1.1;
	br label %for.cond.3;
for.cond.3:
	%29 = load i32, ptr %i.1.1;
	%30 = icmp slt i32 %29, 100;
	br i1 %30, label %for.body.3,label %for.end.3;
for.body.3:
	%31 = load i32, ptr %i.1.1;
	%32 = icmp sgt i32 %31, 20;
	%33 = icmp ne i1 %32, 0;
	br i1 %33, label %logic.rhs.4,label %logic.end.4;
logic.rhs.4:
	%34 = load i32, ptr %i.1.1;
	%35 = icmp slt i32 %34, 80;
	%36 = icmp ne i1 %35, 0;
	br label %logic.end.4;
logic.end.4:
	%37 = phi i1 [ 0, %for.body.3 ], [ %36, %logic.rhs.4 ]
	br i1 %37, label %if.then.5,label %if.else.5;
if.then.5:
	%38 = load i32, ptr %j.1.1;
	store i32 0, ptr %j.1.1;
	br label %for.cond.6;
for.cond.6:
	%39 = load i32, ptr %j.1.1;
	%40 = icmp slt i32 %39, 100;
	br i1 %40, label %for.body.6,label %for.end.6;
for.body.6:
	%41 = load i32, ptr %j.1.1;
	%42 = icmp sgt i32 %41, 5;
	%43 = icmp ne i1 %42, 0;
	br i1 %43, label %logic.end.7,label %logic.rhs.7;
logic.rhs.7:
	%44 = load i32, ptr %i.1.1;
	%45 = icmp slt i32 %44, 90;
	%46 = icmp ne i1 %45, 0;
	br label %logic.end.7;
logic.end.7:
	%47 = phi i1 [ 1, %for.body.6 ], [ %46, %logic.rhs.7 ]
	br i1 %47, label %if.then.8,label %if.else.8;
if.then.8:
	%48 = load i32, ptr %quotient.1.1;
	%49 = load i32, ptr %j.1.1;
	%50 = mul i32 %49, 4
	%51 = sdiv i32 %50, 100
	store i32 %51, ptr %quotient.1.1;
	%52 = load i32, ptr %remainder.1.1;
	%53 = load i32, ptr %j.1.1;
	%54 = mul i32 %53, 4
	%55 = srem i32 %54, 100
	store i32 %55, ptr %remainder.1.1;
	%56 = load ptr, ptr %a.1.1;
	%57 = load i32, ptr %i.1.1;
	%58 = load i32, ptr %quotient.1.1;
	%59 = add i32 %57, %58
	%60 = getelementptr ptr, ptr %56, i32 %59;
	%61 = load ptr, ptr %60;
	%62 = load i32, ptr %remainder.1.1;
	%63 = getelementptr ptr, ptr %61, i32 %62;
	%64 = load i32, ptr %63;
	%65 = load i32, ptr %j.1.1;
	%66 = sub i32 100, 1
	%67 = add i32 %66, 1
	%68 = sub i32 %67, 1
	%69 = add i32 %68, 1
	%70 = sdiv i32 %69, 2
	%71 = add i32 %65, %70
	store i32 %71, ptr %63;
	br label %if.end.8;
if.else.8:
	br label %if.end.8;
if.end.8:
	br label %for.step.6;
for.step.6:
	%72 = load i32, ptr %j.1.1;
	%73 = add i32 %72, 1
	store i32 %73, ptr %j.1.1;
	br label %for.cond.6;
for.end.6:
	br label %if.end.5;
if.else.5:
	br label %if.end.5;
if.end.5:
	br label %for.step.3;
for.step.3:
	%74 = load i32, ptr %i.1.1;
	%75 = add i32 %74, 1
	store i32 %75, ptr %i.1.1;
	br label %for.cond.3;
for.end.3:
	%76 = load i32, ptr %i.1.1;
	store i32 0, ptr %i.1.1;
	br label %for.cond.9;
for.cond.9:
	%77 = load i32, ptr %i.1.1;
	%78 = icmp slt i32 %77, 100;
	br i1 %78, label %for.body.9,label %for.end.9;
for.body.9:
	%79 = load i32, ptr %j.1.1;
	store i32 0, ptr %j.1.1;
	br label %for.cond.10;
for.cond.10:
	%80 = load i32, ptr %j.1.1;
	%81 = icmp slt i32 %80, 100;
	br i1 %81, label %for.body.10,label %for.end.10;
for.body.10:
	%82 = load i32, ptr %sum.1.1;
	%83 = load i32, ptr %sum.1.1;
	%84 = load ptr, ptr %a.1.1;
	%85 = load i32, ptr %i.1.1;
	%86 = getelementptr ptr, ptr %84, i32 %85;
	%87 = load ptr, ptr %86;
	%88 = load i32, ptr %j.1.1;
	%89 = getelementptr ptr, ptr %87, i32 %88;
	%90 = load i32, ptr %89;
	%91 = add i32 %83, %90
	store i32 %91, ptr %sum.1.1;
	br label %for.step.10;
for.step.10:
	%92 = load i32, ptr %j.1.1;
	%93 = add i32 %92, 1
	store i32 %93, ptr %j.1.1;
	br label %for.cond.10;
for.end.10:
	br label %for.step.9;
for.step.9:
	%94 = load i32, ptr %i.1.1;
	%95 = add i32 %94, 1
	store i32 %95, ptr %i.1.1;
	br label %for.cond.9;
for.end.9:
	%96 = load i32, ptr %sum.1.1;
	%97 = call ptr @toString(i32 %96)
	call void @println(ptr %97)
	ret i32 0;
}

