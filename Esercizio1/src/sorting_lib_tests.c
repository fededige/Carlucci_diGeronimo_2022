#include <stdio.h>
#include <stdlib.h>
#include "../lib/unity.h"
#include "sorting_lib.h"
#include "DynamicArray_lib.h"

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

static void test_array_is_empty_zero_el() {
  Array *array = array_create(precedes_int);
  TEST_ASSERT_TRUE(array_is_empty(array));
  array_free_memory(array);
}

static void test_array_is_empty_one_el() {
  int i = -12;
  Array *array = array_create(precedes_int);
  array_insert(array, &i);
  TEST_ASSERT_FALSE(array_is_empty(array));
  array_free_memory(array);
}

static void test_array_size_zero_el() {
  Array *array = array_create(precedes_int);
  TEST_ASSERT_EQUAL_INT(0, array_size(array));
  array_free_memory(array);
}

static void test_array_size_one_el() {
  int i1 = -12;
  Array *array = array_create(precedes_int);
  array_insert(array, &i1);
  TEST_ASSERT_EQUAL_INT(1, array_size(array));
  array_free_memory(array);
}

static void test_array_size_two_el() {
  int i1 = -12;
  int i2 = 2;
  Array *array = array_create(precedes_int);
  array_insert(array, &i1);
  array_insert(array, &i2);
  TEST_ASSERT_EQUAL_INT(2, array_size(array));
  array_free_memory(array);
}

static void test_array_bi_srt_one_el() {
  int i1 = -12;
  Array *array = array_create(precedes_int);
  array_insert(array, &i1);
  bi_insertion_sort(array->array, array->size, precedes_int);
  TEST_ASSERT_EQUAL_PTR(&i1, array_get(array, 0));
  array_free_memory(array);
}

static void test_array_bi_srt_three_el() {
  int i1 = -12;
  int i2 = 0;
  int i3 = 4;
  int* exp_arr[] = {&i1, &i2, &i3};

  Array *array = array_create(precedes_int);

  array_insert(array, &i2);
  array_insert(array, &i3);
  array_insert(array, &i1);

  bi_insertion_sort(array->array, array->size, precedes_int);

  int **act_arr = malloc(3*sizeof(int*));
  for (unsigned long i=0; i < 3; ++i) {
    act_arr[i] = (int*)array_get(array, i);
  }
  TEST_ASSERT_EQUAL_PTR_ARRAY(exp_arr, act_arr, 3);
  free(act_arr);
  array_free_memory(array);
}

static void test_array_rnd_qck_one_el() {
  int i1 = -12;
  Array *array = array_create(precedes_int);
  array_insert(array, &i1);
  quicksort(array->array, 0, array->size, precedes_int);
  TEST_ASSERT_EQUAL_PTR(&i1, array_get(array, 0));
  array_free_memory(array);
}

static void test_array_rnd_qck_three_el() {
  int i1 = -12;
  int i2 = 0;
  int i3 = 4;
  int* exp_arr[] = {&i1, &i2, &i3};

  Array *array = array_create(precedes_int);

  array_insert(array, &i2);
  array_insert(array, &i3);
  array_insert(array, &i1);

  quicksort(array->array, 0, array->size, precedes_int);

  int **act_arr = malloc(3*sizeof(int*));
  for (unsigned long i=0; i < 3; ++i) {
    act_arr[i] = (int*)array_get(array, i);
    /*printf("%d\n", *act_arr[i]);*/
  }
  TEST_ASSERT_EQUAL_PTR_ARRAY(exp_arr, act_arr, 3);
  free(act_arr);
  array_free_memory(array);
}

static void test_array_qck_one_el() {
  int i1 = -12;
  Array *array = array_create(precedes_int);
  array_insert(array, &i1);
  quicksort(array->array, 1, array->size, precedes_int);
  TEST_ASSERT_EQUAL_PTR(&i1, array_get(array, 0));
  array_free_memory(array);
}

static void test_array_qck_three_el() {
  int i1 = -12;
  int i2 = 0;
  int i3 = 4;
  int* exp_arr[] = {&i1, &i2, &i3};

  Array *array = array_create(precedes_int);

  array_insert(array, &i2);
  array_insert(array, &i3);
  array_insert(array, &i1);

  quicksort(array->array, 1, array->size, precedes_int);

  int **act_arr = malloc(3*sizeof(int*));
  for (unsigned long i=0; i < 3; ++i) {
    act_arr[i] = (int*)array_get(array, i);
  }
  TEST_ASSERT_EQUAL_PTR_ARRAY(exp_arr, act_arr, 3);
  free(act_arr);
  array_free_memory(array);
}

static void test_array_qck_zero_el() {
  Array *array = array_create(precedes_int);
  quicksort(array->array, 1, array->size, precedes_int);
  TEST_ASSERT_TRUE(array_is_empty(array));
  array_free_memory(array);
}
static void test_array_rnd_qck_zero_el() {
  Array *array = array_create(precedes_int);
  quicksort(array->array, 0, array->size, precedes_int);
  TEST_ASSERT_TRUE(array_is_empty(array));
  array_free_memory(array);
}
static void test_array_bi_srt_zero_el() {
  Array *array = array_create(precedes_int);
  quicksort(array->array, 1, array->size, precedes_int);
  TEST_ASSERT_TRUE(array_is_empty(array));
  array_free_memory(array);
}

int main(){
    UNITY_BEGIN();
    RUN_TEST(test_array_is_empty_zero_el);
    RUN_TEST(test_array_is_empty_one_el);
    RUN_TEST(test_array_size_zero_el);
    RUN_TEST(test_array_size_one_el);
    RUN_TEST(test_array_size_two_el);
    RUN_TEST(test_array_bi_srt_one_el);
    RUN_TEST(test_array_bi_srt_three_el);
    RUN_TEST(test_array_rnd_qck_one_el);
    RUN_TEST(test_array_rnd_qck_three_el);
    RUN_TEST(test_array_qck_one_el);
    RUN_TEST(test_array_qck_three_el);
    RUN_TEST(test_array_qck_zero_el);
    RUN_TEST(test_array_rnd_qck_zero_el);
    RUN_TEST(test_array_bi_srt_zero_el);
    return UNITY_END();
}