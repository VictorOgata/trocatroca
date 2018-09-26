
        if (word.equals("")){
            query = myRef;
            query = myRef.startAt(word).endAt(word+"\uf8ff");