#include "skip_list_lib.h"
#include <stdlib.h>
#include <stdio.h> 
#include <time.h>


void *list_get(Node *node){
    if(node == NULL){
        fprintf(stderr, "list_get: node parameter cannot be NULL");
        return NULL;
    }
    return node->item;
}

SkipList *CreateSkipList(int (*compare) (void *a, void *b)){
    if(compare == NULL){
        fprintf(stderr, "CreateSkipList: compare parameter cannot be NULL");
        return NULL;
    }

    SkipList *list = (SkipList*) malloc(sizeof(SkipList));
    if(list == NULL){
        fprintf(stderr, "CreateSkipList: unable to allocate memory for the SkipList");
    }

    /*list->head = (Node*) malloc(sizeof(Node));*/
    list->head = CreateNode(NULL, MAX_HEIGHT);
    /*list->head = NULL;*/
    list->max_level = 0;
    list->compare = compare;
    return list;
}

void *searchSkipList(SkipList *list, void* I){
    Node *x = list->head;
    unsigned int i=0;
    
    for(i = list->max_level; i > 0; i--){
        while((x->next[i - 1] != NULL) && list->compare(x->next[i - 1]->item, I) == -1){
            //printf("\nx = x->next[i - 1]\n");
            //list->compare(x->next[i - 1]->item, I);
            x = x->next[i - 1];
        }
    }
    //printf("\nsiamo fuori dal for\n ");
    x = x->next[0];
    if( x == NULL){
        return NULL;
    }
    //printf("INDIRIZZO: %p\n",x->item);
    if(list->compare(x->item, I) == 0){
        //printf("trovato\n");
        return x->item;
    }
    else{
        return NULL;
    }
}
/*
[3]----------------------------------> NIL
[2]----------------------------------> NIL
[1]---------------->[mondo]----------> NIL
[0]->[ciao]->[ehi]->[mondo]->[tutto]-> NIL

*/

void insertSkipList(SkipList *list, void* I){
    Node *new;
    new = CreateNode(I, randomLevel());
    if(new->size > list->max_level){
        list->max_level = new->size;
    }
    if(list->head == NULL){
      list->head = new;
      return;
    }
    Node *x = list->head;
    for(unsigned int k = list->max_level; k > 0; k--){
        if(x->next[k - 1] == NULL || (list->compare(I, x->next[k - 1]->item) == -1)){
            if(k - 1 < new->size){
                new->next[k - 1] = x->next[k - 1];
                x->next[k - 1] = new;
            }
        }
        else{
            x = x->next[k - 1];
            k++;    
        }
    }
}

void free_memory(SkipList *list){
    if(list == NULL){
        fprintf(stderr, "free_SkipList: SkipList parameter cannot be NULL");
        return;
    }
    Node *current = list->head;
    while (current != NULL) {
        Node *next = current->next[0];
        free(current->next);
        free(current);
        current=next;
    }
    free(list);
}

Node *CreateNode(void* I, unsigned int randomlevel){
    Node *x;
    x = (Node*) malloc(sizeof(Node));
    x->size = randomlevel;
    x->item = I;
    x->next = (Node**) malloc(sizeof(Node *) * randomlevel);
    for(unsigned int i = 0; i < randomlevel; i++){
      x->next[i] = NULL;
    }
    return x;
}

unsigned int randomLevel(){
    unsigned int lvl = 1;
    while((double)rand() / (double)RAND_MAX < 0.5 && lvl < MAX_HEIGHT){ //ricordarsi di fare la srand
        lvl = lvl + 1;
    }
    //printf("\n\nlvl %i\n\n", lvl);
    return lvl;
}