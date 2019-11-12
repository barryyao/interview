# interview

SortBigFile
比较文件内容

题目描述：比较N个大小完全相等的文件是否equal，每个文件10G，每个文件的行顺序可能不一样，判断是否equal 的标准是对行排序后，字符一致，请写一个程序，实现这一需求。

输入描述：第一行输入比较文件大小N，第二行开始输入文件路径，每行一个文件路径

输出描述：输出相同文件路径，耗时。

输入例子：

10 
/data/log0.log
/data/log1.log
/data/log2.log
/data/log3.log
/data/log4.log
/data/log5.log
/data/log6.log
/data/log7.log
/data/log8.log
/data/log9.log

输出例子：

/data/log2.log /data/log6.log /data/log8 相同
/data/log0.log /data/log5.log 相同 
4563ms


Lottery
年会抽奖

题目描述：公司年会抽奖，舞台上有4个盒子，其中一个盒子是奖品，另外3个盒子里面是空的。假如你选择了一个盒子，比如1号盒子，这时，主持人会打开另一个盒子，比如3号盒子，里面是空的（主持人知道每个盒子里面是什么）。现在，主持人给你一次重新选择的机会，你是否应该另换一个盒子呢还是维持最初的选择不变？请写一个程序来模拟这个场景，通过足够多次的场景模拟来证明换和不换，中奖的概率分别是多少。

输入描述：输入模拟次数

输出描述：基于输入模拟次数，输出不换盒子的中奖次数，换盒子的中奖次数

输入例子：10000

输出例子：2500，2500



