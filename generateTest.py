def generateTest():
	fp = file("test1", 'w')
	row , col = 500, 500
	fp.write(str(row) + " " + str(col) + "\n")
	for x in xrange(row):
		for y in xrange(col):
			fp.write("99999 ")
		fp.write("\n")
	fp.close()
def generateTest2():
	fp = file("test2", 'w')
	row , col = 500, 500
	fp.write(str(row) + " " + str(col) + "\n")
	for x in xrange(row):
		for y in xrange(col):
			if x == y or x+y == 499:
				fp.write('-1   ')
			else:
				fp.write("99999 ")
		fp.write("\n")
	fp.close()
if __name__ == "__main__":
	generateTest()