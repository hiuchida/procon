#include <stdio.h>
#include <time.h>
#include <sys/time.h>
#include <string.h>
#include <sys/resource.h>

void solve();
void readNAndList(int* n, char list[50][6]);
int toDecimal(char str[501], int n, char list[50][6]);

int main(int argc, char** argv) {
	struct timeval S, G;
	struct rusage r;

	gettimeofday(&S, NULL);

	solve();

	gettimeofday(&G, NULL);
	time_t secDiff = difftime(G.tv_sec, S.tv_sec);
	suseconds_t usecDiff = G.tv_usec - S.tv_usec;
	fprintf(stderr, "%ldms\n", secDiff * 1000 + usecDiff / 1000);

	getrusage(RUSAGE_SELF, &r);
	fprintf(stderr, "maxrss=%ldKB\n", r.ru_maxrss);

	return 0;
}

void solve() {
	int A, B, C;
	char x[50][6], y[50][6], z[50][6];
	int K;
	char S[501], T[501];
	int s, t, sum;
	char U[501] = "";
	int cWidth;

	readNAndList(&A, x);
	readNAndList(&B, y);
	readNAndList(&C, z);

	fscanf(stdin, "%d", &K);
	fscanf(stdin, "%s", S);
	fscanf(stdin, "%s", T);

	s = toDecimal(S, A, x);
	t = toDecimal(T, B, y);

	sum = s + t;

	cWidth = strlen(z[0]);
	for (int idx = cWidth * (K - 1); idx >= 0; idx -= cWidth) {
		strncpy(&U[idx], z[sum % C], cWidth);
		sum /= C;
	}

	fprintf(stdout, "%s\n", U);
}

void readNAndList(int* n, char list[50][6]) {
	fscanf(stdin, "%d", n);
	for (int i = 0; i < *n; ++i) {
		fscanf(stdin, "%s", list[i]);
	}
}

int toDecimal(char str[501], int n, char list[50][6]) {
	int res = 0;

	int len = strlen(list[0]);

	for (int i = 0; i < strlen(str); i += len) {
		int idx = 0;
		char tmp[6] = "";
		strncpy(tmp, &str[i], len);
		for (; idx < n; ++idx) {
			if (strcmp(list[idx], tmp) == 0) {
				break;
			}
		}
		res = res * n + idx;
	}

	return res;
}
