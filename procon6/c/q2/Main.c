#include <stdio.h>
#include <string.h>
#include <time.h>
#include <sys/time.h>
#include <sys/resource.h>

int main(int argc, char** argv) {
	char buf[300003];

	char stack[300000];
	int sp;

	struct timeval S, G;
	struct rusage r;

	gettimeofday(&S, NULL);

	fgets(buf, sizeof(buf), stdin);
	while (buf[strlen(buf) - 1] == '\n' || buf[strlen(buf) - 1] == '\r') {
		buf[strlen(buf) - 1] = '\0';
	}

	sp = 0;
	int len = strlen(buf);
	for (int i = 0; i < len; ++i) {
		char ch = buf[i];
		if (sp == 0) {
			stack[sp++] = ch;
		} else {
			char top = stack[sp - 1];
			if (top == 'G' && ch == 'C'
			|| top == 'C' && ch == 'P'
			|| top == 'P' && ch == 'G') {
				--sp;
			} else {
				stack[sp++] = ch;
			}
		}
	}
	printf("%d\n", sp);

	gettimeofday(&G, NULL);
	time_t secDiff = difftime(G.tv_sec, S.tv_sec);
	suseconds_t usecDiff = G.tv_usec - S.tv_usec;
	fprintf(stderr, "%ldms\n", secDiff * 1000 + usecDiff / 1000);

	getrusage(RUSAGE_SELF, &r);
	fprintf(stderr, "maxrss=%ldKB\n", r.ru_maxrss);

	return 0;
}
