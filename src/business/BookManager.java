package business;

import core.Helper;
import dao.BookDao;
import entity.Book;

import java.util.ArrayList;

public class BookManager {

    private final BookDao bookDao;

    public BookManager(){
        this.bookDao= new BookDao();

    }

    //bookDao üzerinden getById metodu ile belirli bir id çağırılır.
    public Book getById(int id){return this.bookDao.getById(id);}

    //bookDao üzerinden tüm rezervasyonları almak için Arraylist döndürülür.
    public ArrayList<Book> findAll(){return this.bookDao.findAll();}

    // size her bir satırda bulunacak sütun sayısını belirtir. rentalList tablo gösterimine dönüştürülecek olan Book nesnelerini içeren bir listedir
    // i = 0 olduğu için tablo numaraları birer birer artırılır ve tabloya yerleştirilir.
    public ArrayList<Object[]> getForTable(int size ,ArrayList<Book>rentalList){
        ArrayList<Object[]> rentalObjList = new ArrayList<>();
        for(Book obj : rentalList){
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getCar().getPlate();
            rowObject[i++] = obj.getCar().getModel().getBrand().getName();
            rowObject[i++] = obj.getCar().getModel().getName();
            rowObject[i++] = obj.getName();
            rowObject[i++] = obj.getMpno();
            rowObject[i++] = obj.getMail();
            rowObject[i++] = obj.getIdno();
            rowObject[i++] = obj.getStrt_date();
            rowObject[i++] = obj.getFnsh_date();
            rowObject[i++] = obj.getPrc();
            rentalObjList.add(rowObject);
        }
        return  rentalObjList;
    }

    //carId ile eşleşen rezervasyonları getirmek için kullanılır.
    public ArrayList<Book> searchForTable(int carId){
        String select ="SELECT * FROM public.book ";
        ArrayList<String> whereList = new ArrayList<>();


        //Eğer carId 0'dan farklıysa, sadece belirli bir araba kimliği ile ilişkilendirilmiş kitaplar getirilir.
        //Eğer carId 0 ise, tüm kitaplar getirilir.
        if (carId != 0){
            whereList.add("book_car_id = " +carId );

        }
        String whereStr = String.join(" AND ",whereList);
        // query güncellenir.
        String query=select;
        //birden çok id varsa, sorguya WHERE ekle.
        if(whereStr.length() > 0){

            query +=  " WHERE "+whereStr;

        }
        // İşlemleri yapar ve çıkan sonucu Db'e döndürür ve manipüle eder.
        return this.bookDao.selectByQuery(query);

    }
    //DB veri siler
    public boolean delete(int id ){

        if(this.getById(id)==null){
            Helper.showMsg(id+" ID kayıtlı model bulunamadı");
            return false;
        }
        return this.bookDao.delete(id);
    }
    // Db veri ekler.
    public boolean save(Book book){
        return this.bookDao.save(book);
    }


}
