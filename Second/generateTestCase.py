import random


def generateTestCase(filename):
	cities = 100000
	queries = 100000

	POINTS = [i for i in range(1, cities+1)]
	random.shuffle(POINTS)
	#print POINTS
	fp = file(filename, 'w')
	fp.write(str(cities) + " " + str(queries) + "\n")

	for i in xrange(1,cities):
		fp.write(str(POINTS[random.randint(0, i-1)]) + " " + str(POINTS[i]) + "\n")

	for i in xrange(1, queries+1):
		fp.write(str(random.randint(1, 2)) + " " + str(random.randint(1, cities)) + "\n")

	fp.close()

def generateTestCaseLine(filename):
	cities = 100000
	queries = 100000

	POINTS = [i for i in range(1, cities+1)]
	#random.shuffle(POINTS)
	#print POINTS
	fp = file(filename, 'w')
	fp.write(str(cities) + " " + str(queries) + "\n")

	for i in xrange(1,cities):
		fp.write(str(POINTS[i-1]) + " " + str(POINTS[i]) + "\n")

	for i in xrange(1, queries+1):
		fp.write(str(random.randint(1, 2)) + " " + str(POINTS[i-1]) + "\n")

	fp.close()
generateTestCaseLine("testcase1")
generateTestCase("testcase2")