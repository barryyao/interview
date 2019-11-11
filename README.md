# interview

SortBigFile

题目描述：比较N个大小完全相等的文件是否equal，每个文件10G，每个文件的行顺序可能不一样，判断是否equal 的标准是对行排序后，字符一致，请写一个程序，实现这一需求。

输入描述：第一行输入比较文件大小N，第二行开始输入文件路径，每行一个文件路径

输出描述：输出相同文件路径，耗时。

输入例子：

10 /data/log0.log /data/log1.log /data/log2.log /data/log3.log /data/log4.log /data/log5.log /data/log6.log /data/log7.log /data/log8.log /data/log9.log

输出例子：

/data/log2.log /data/log6.log /data/log8 相同 /data/log0.log /data/log5.log 相同 
4563ms
