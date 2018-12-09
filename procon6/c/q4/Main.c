#include <stdio.h>
#include <time.h>
#include <sys/time.h>
#include <sys/resource.h>

int main(int argc, char** argv) {
	long A, B, K;
	struct timeval S, G;
	struct rusage r;

	gettimeofday(&S, NULL);

	scanf("%ld %ld %ld", &A, &B, &K);

	long a = (A >= 0 ? (A + K - 1) / K : A / K);
	long b = (B >= 0 ? (B + K - 1) / K : B / K);
	long ans = K * ((b - 1) * b / 2 - (a - 1) * a / 2);
	fprintf(stdout, "%ld\n", ans);

	gettimeofday(&G, NULL);
	time_t secDiff = difftime(G.tv_sec, S.tv_sec);
	suseconds_t usecDiff = G.tv_usec - S.tv_usec;
	fprintf(stderr, "%ldms\n", secDiff * 1000 + usecDiff / 1000);

	getrusage(RUSAGE_SELF, &r);
	fprintf(stderr, "maxrss=%ldKB\n", r.ru_maxrss);

	return 0;
}
