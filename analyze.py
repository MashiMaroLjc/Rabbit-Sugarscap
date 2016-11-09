#coding:utf-8

file_name = "logs.txt"
infile = open(file_name)

out_image = "rich_people.jpg"


t = "=====================================\n"

USER_ID = "92"
begin_point = (0,0)
path = []
sugar_tran = []
# 监测id为512的糖人的变化规律
first = True
for file_line in infile:
	if file_line != t:
		file_line = file_line.strip().split("\t")
		# print(file_line)
		user_id = file_line[1] # 用户id
		if user_id != USER_ID:
			continue
		if first:
			first = False
			_from = file_line[2]
			# 记录起点
			temp = _from.replace("from:","").split(",")
			begin_point = (int(temp[0]),int(temp[1]))
			path.append(begin_point)
		if file_line[2] == "die!" or file_line[2].startswith("Lest:"):
			break
		else:
			to = file_line[3]
			temp = to.replace("To:","").split(",")
			to_point = (int(temp[0]),int(temp[1]))
			path.append(to_point)
			sugar = file_line[6].replace("Sugar:","")
			sugar_tran.append(float(sugar))


import matplotlib.pyplot as plt 

plt.plot([i + 1 for i in range(len(sugar_tran))],sugar_tran)
plt.title("ID:%s"%(USER_ID))
plt.savefig(out_image)
