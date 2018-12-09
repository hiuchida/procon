#include <stdio.h>
#include <time.h>
#include <sys/time.h>
#include <sys/resource.h>

int main(int argc, char** argv) {
	char gengo;
	int jYear;
	int year;

	struct timeval S, G;
	struct rusage r;

	gettimeofday(&S, NULL);

	fscanf(stdin, "%c%d", &gengo, &jYear);
	switch (gengo) {
		case 'M':
			year = 1868 + jYear - 1;
			break;
		case 'T':
			year = 1912 + jYear - 1;
			break;
		case 'S':
			year = 1926 + jYear - 1;
			break;
		case 'H':
			year = 1989 + jYear - 1;
			break;
		case 'X':
			year = 2019 + jYear - 1;
			break;
		default:
			return 1;
	}
	printf("%d\n", year);

	gettimeofday(&G, NULL);
	time_t secDiff = difftime(G.tv_sec, S.tv_sec);
	suseconds_t usecDiff = G.tv_usec - S.tv_usec;
	fprintf(stderr, "%ldms\n", secDiff * 1000 + usecDiff / 1000);

	getrusage(RUSAGE_SELF, &r);
	fprintf(stderr, "maxrss=%ldKB\n", r.ru_maxrss);

	return 0;
}
