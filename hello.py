from sklearn import tree

features = [[89], [81], [73], [76]]
labels = ["A+", "A+", "A-", "A"]

print("Features", features)
print("Labels", labels)

classifier = tree.DecisionTreeClassifier()
classifier.fit(features, labels)

score = 67
print("Predicting ", score, "Result", classifier.predict([[score]]))

