.PHONY: benchmark

benchmark:
	./gradlew --no-daemon jmh
