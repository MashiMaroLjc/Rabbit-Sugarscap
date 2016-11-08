import numpy as np
import matplotlib.pyplot as plt
import mpl_toolkits.mplot3d
import json

def draw(infile,outfile):
	json_ = json.load(open(infile))
	z = np.array(json_)
	x = [i for i in range(z.shape[0])]
	y = [i for i in range(z.shape[1])]
	x,y = np.meshgrid(x,y)
	ax=plt.subplot(111,projection='3d')
	ax.plot_surface(x,y,z,rstride=2,cstride=1,cmap=plt.cm.coolwarm,alpha=0.8)
	ax.set_xlabel('x')
	ax.set_ylabel('y')
	ax.set_zlabel('z')
	plt.savefig(outfile)

draw("begin_sugar.txt", "begin_sugar.jpg")
draw("begin.txt", "begin.jpg")
draw("end.txt", "end.jpg")