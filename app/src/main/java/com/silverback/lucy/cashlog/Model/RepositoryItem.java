package com.silverback.lucy.cashlog.Model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.silverback.lucy.cashlog.Model.DatabaseLocal.MyRoomDatabase;
import com.silverback.lucy.cashlog.Model.POJO.Item;

import java.util.List;

public class RepositoryItem {

    private DaoItem mDaoItem;
    private LiveData<List<Item>> mAllItems;

    RepositoryItem(Application application){
        MyRoomDatabase db = MyRoomDatabase.getINSTANCE(application);
        mDaoItem = db.daoItem();
        mAllItems = mDaoItem.getAllItems();
    }       //end constructor()


    LiveData<List<Item>> getAllItems(){
        return mAllItems;
    }       //end getAllItems()


    public void insert(Item item){
        new InsertAsyncTask(mDaoItem).execute(item);
    }       //end insert()


    public void update(Item item){
        new UpdateAsyncTask(mDaoItem).execute(item);
    }       //end update()


    public void delete(Item item){
        new DeleteAsyncTask(mDaoItem).execute(item);
    }       //end delete()


    public void deleteAllItems(){
        new DeleteAllAsyncTask(mDaoItem).execute();
    }       //end deleteAllItems()



    //------------------------------------------------------ASYNC OPERATIONS----------------------------
    private static class InsertAsyncTask extends AsyncTask<Item, Void, Void>{
        private DaoItem mAsyncTaskDao;

        public InsertAsyncTask(DaoItem mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }       //end constructor()

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }       //end innerClass


    private static class UpdateAsyncTask extends AsyncTask<Item, Void, Void>{
        private DaoItem mAsyncTaskDao;

        public UpdateAsyncTask(DaoItem mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }       //end constructor()

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }       //end innerClass


    private static class DeleteAsyncTask extends AsyncTask<Item, Void, Void>{
        private DaoItem mAsyncTaskDao;

        public DeleteAsyncTask(DaoItem mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }       //end constructor()

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }       //end innerClass


    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void>{
        private DaoItem mAsyncTaskDao;

        public DeleteAllAsyncTask(DaoItem mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }       //end constructor()

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllItems();
            return null;
        }
    }       //end innerClass
//==========================================================END ASYNC OPERATIONS ===========================

}       //end class
