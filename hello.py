from sklearn import tree

data = []

with open("grades.csv", "r") as input:
    lineNumber = 0
    for line in input:
        if lineNumber != 0:
            tokens = line.split(",")
            data.append(tokens)
        lineNumber += 1
print("Read", lineNumber, "lines")

labels = []
features = []
for row in data:
    label = row[1]
    labels.append(label)
    
    featureVector = []
    feature = float(row[0])
    featureVector.append(feature)
    features.append(featureVector)

print("Features", features)
print("Labels", labels)

classifier = tree.DecisionTreeClassifier()
classifier.fit(features, labels)

score = 67
print("Predicting ", score, "Result", classifier.predict([[score]]))

