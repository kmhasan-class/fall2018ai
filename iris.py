from sklearn import tree
import random

data = []
test = []

with open("iris.csv", "r") as input:
    lineNumber = 0
    for line in input:
        if lineNumber != 0:
                tokens = line.split(",")
                data.append(tokens)
        lineNumber += 1
print("Read", lineNumber, "lines")

random.shuffle(data)

labels = []
features = []

trainingFeatures = []
trainingLabels = []

testFeatures = []
testLabels = []

rowNumber = 0
trainingDataCount = int(len(data) * 0.5)
for row in data:
    label = row[4]
    
    featureVector = []
    feature = float(row[0])
    featureVector.append(feature)
    feature = float(row[1])
    featureVector.append(feature)
    feature = float(row[2])
    featureVector.append(feature)
    feature = float(row[3])
    featureVector.append(feature)

    if rowNumber < trainingDataCount:
        trainingFeatures.append(featureVector)
        trainingLabels.append(label)
    else:
        testFeatures.append(featureVector)
        testLabels.append(label)
    
    rowNumber += 1

print("Number of records in the training set", len(trainingLabels))
print("Number of records in the test set", len(testLabels))

classifier = tree.DecisionTreeClassifier()
classifier.fit(trainingFeatures, trainingLabels)

prediction = classifier.predict(testFeatures)

for i in range(0, len(testFeatures)):
    print(i, prediction[i] == testLabels[i])

#HW: calculate the accuracy
