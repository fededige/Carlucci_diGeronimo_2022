#include "skip_list_lib.h"
#include <stdlib.h>
#include <stdio.h>

SkipList *CreateSkipList(int (*compare) (void *a, void *b)){
    if(compare == NULL){
        fprintf(stderr, "CreateSkipList: compare parameter cannot be NULL");
        return NULL;
    }
    SkipList *list = (SkipList*) malloc(sizeof(SkipList));

    if(list == NULL){
        fprintf(stderr, "CreateSkipList: unable to allocate memory for the SkipList");
    }
    list->head = (Node*) malloc(sizeof(Node));
    list->max_level = 0;
    list->compare = compare;
    return list;
}

void *searchSkipList(SkipList *list, void* I){
    Node *x = list->head;
    unsigned int i=0;
    
    for(i = list->max_level; i > 0; i--){
        while(x->next[i]->item < I){
            x = x->next[i];
        }
    }
    x = x->next[1];
    if(x->item == I){
        return x->item;
    }
    else{
        return NULL;
    }
}

void insertSkipList(SkipList *list, void* I){
    Node *new;
    new = CreateNode(I, randomLevel());
    if(new->size > list->max_level){
        list->max_level = new->size;
    }
    Node *x = list->head;
    for(unsigned int k = list->max_level; k > 0; k--){
        if(x->next[k] == NULL || I < x->next[k]->item){
            if(k < new->size){
                new->next[k] = x->next[k];
                x->next[k] = new;
            }
        }
        else{
            x = x->next[k];
            k++;    
        }
    }
}

void free_Node(Node *node, unsigned int i){
    if (node == NULL) {
        return;
    }
    free_Node(node->next[i], i);
    
    free(node);
}

void free_SkipList(SkipList *list){
    if(list == NULL){
        fprintf(stderr, "free_SkipList: SkipList parameter cannot be NULL");
        return;
    }

    Node *node = list->head;
    
    for(unsigned int i = node->size; i > 0; i--){
        free_Node(node->next[i], i);
    }

    free(node);
    free(list);
}

Node *CreateNode(void* I, unsigned int randomlevel){
    Node *x;
    x = malloc(sizeof(Node));
    x->size = randomlevel;
    x->item = I;
    x->next = malloc(sizeof(Node) * randomlevel);
    return x;
}

unsigned int randomLevel(){
    unsigned int lvl = 1;
    while(random() < 0.5 && lvl < MAX_HEIGHT){
        lvl = lvl + 1;
    }
    return lvl;
}