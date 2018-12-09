#include <stdio.h>
#include <string.h>

int main(int argc, char** argv) {
	int map[128];

	char s[1024];

	int prev = 0;
	int ans = 0;
	int num;
	int sign = 1;

	map['I'] = 1;
	map['V'] = 5;
	map['X'] = 10;
	map['L'] = 50;
	map['C'] = 100;
	map['D'] = 500;
	map['M'] = 1000;

	fgets(s, sizeof(s), stdin);

	for (int i = strlen(s) - 1; i >= 0; --i) {
		num = map[s[i]];

		if (num > prev) {
			sign = 1;
		} else if (num < prev) {
			sign = -1;
		}
		ans += num * sign;

		prev = num;
	}

	fprintf(stdout, "%d\n", ans);

	return 0;
}
