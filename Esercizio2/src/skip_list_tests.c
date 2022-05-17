#include <stdio.h>
#include <stdlib.h>
#include "../lib/unity.h"
#include "skip_list_lib.h"

// precedence relation used in tests
static int precedes_int(void *i1_p, void *i2_p) {
  int *int1_p = (int*)i1_p;
  int *int2_p = (int*)i2_p;
  if (*int1_p < *int2_p){
    return -1;
  }
  else if(*int1_p > *int2_p){
    return 1;
  }
  else{
    return 0;
  }
}

static void test_skipList_is_empty_zero_el() {
  SkipList *list = CreateSkipList(precedes_int);
  TEST_ASSERT_TRUE(skipList_is_empty(list));
  free_memory(list);
}

static void test_skipList_is_empty_one_el() {
  int i = -12;
  SkipList *list = CreateSkipList(precedes_int);
  insertSkipList(list, &i);
  TEST_ASSERT_FALSE(skipList_is_empty(list));
  free_memory(list);
}

static void test_skipList_search_one_el() {
  int i1 = -12;
  SkipList *list = CreateSkipList(precedes_int);
  insertSkipList(list, &i1);
  TEST_ASSERT_EQUAL_PTR(&i1, searchSkipList(list, &i1));
  free_memory(list);
}

static void test_skipList_bi_srt_three_el() {
  int i1 = -12;
  int i2 = 0;
  int i3 = 4;

  SkipList *list = CreateSkipList(precedes_int);
  insertSkipList(list, &i2);
  insertSkipList(list, &i3);
  insertSkipList(list, &i1);

  TEST_ASSERT_EQUAL_PTR(&i1, searchSkipList(list, &i1));
  TEST_ASSERT_EQUAL_PTR(&i2, searchSkipList(list, &i2));
  TEST_ASSERT_EQUAL_PTR(&i3, searchSkipList(list, &i3));

  free_memory(list);
}

static void test_skipList_size_zero_el() {
 SkipList *list = CreateSkipList(precedes_int);
  TEST_ASSERT_EQUAL_INT(0, SkipList_size(list));
  free_memory(list);
}

static void test_skipList_size_one_el() {
  int i1 = -12;
  SkipList *list = CreateSkipList(precedes_int);
  insertSkipList(list, &i1);
  TEST_ASSERT_EQUAL_INT(1, SkipList_size(list));
  free_memory(list);
}

static void test_skipList_size_two_el() {
  int i1 = -12;
  int i2 = 2;
  SkipList *list = CreateSkipList(precedes_int);
  insertSkipList(list, &i1);
  insertSkipList(list, &i2);
  TEST_ASSERT_EQUAL_INT(2, SkipList_size(list));
  free_memory(list);
}

int main(){
    UNITY_BEGIN();
    RUN_TEST(test_skipList_is_empty_zero_el);
    RUN_TEST(test_skipList_is_empty_one_el);
    RUN_TEST(test_skipList_search_one_el);
    RUN_TEST(test_skipList_bi_srt_three_el);
    RUN_TEST(test_skipList_size_zero_el);
    RUN_TEST(test_skipList_size_one_el);
    RUN_TEST(test_skipList_size_two_el);
    return UNITY_END();
}