cd ..
md tmp
del tmp\*.txt
java -cp bin Main < data\in\q11.txt > tmp\q11.txt
java -cp bin Main < data\in\q12.txt > tmp\q12.txt
java -cp bin Main < data\in\q13.txt > tmp\q13.txt
java -cp bin Main < data\in\q2.txt > tmp\q2.txt
java -cp bin Main < data\in\q3.txt > tmp\q3.txt
java -cp bin Main < data\in\s1.txt > tmp\s1.txt
java -cp bin Main < data\in\s2.txt > tmp\s2.txt
fc tmp\q11.txt data\out\q11.txt
fc tmp\q12.txt data\out\q12.txt
fc tmp\q13.txt data\out\q13.txt
fc tmp\q2.txt data\out\q2.txt
fc tmp\q3.txt data\out\q3.txt
fc tmp\s1.txt data\out\s1.txt
fc tmp\s2.txt data\out\s2.txt
pause
