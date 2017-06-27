package star.liuwen.com.rxeasyhttp.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuwen on 2017/6/26.
 */
public class Test implements Serializable {


    /**
     * code : 1
     * message : ok
     * data : {"male":[{"name":"玄幻","bookCount":454052},{"name":"奇幻","bookCount":42150},{"name":"武侠","bookCount":37203},{"name":"仙侠","bookCount":118776},{"name":"都市","bookCount":317079},{"name":"职场","bookCount":14122},{"name":"历史","bookCount":62950},{"name":"军事","bookCount":13585},{"name":"游戏","bookCount":74378},{"name":"竞技","bookCount":5266},{"name":"科幻","bookCount":101376},{"name":"灵异","bookCount":30010},{"name":"同人","bookCount":35626},{"name":"轻小说","bookCount":4484}],"female":[{"name":"古代言情","bookCount":383486},{"name":"现代言情","bookCount":455224},{"name":"青春校园","bookCount":99552},{"name":"纯爱","bookCount":116090},{"name":"玄幻奇幻","bookCount":117492},{"name":"武侠仙侠","bookCount":57663},{"name":"科幻","bookCount":6331},{"name":"游戏竞技","bookCount":5027},{"name":"悬疑灵异","bookCount":11650},{"name":"同人","bookCount":119975},{"name":"女尊","bookCount":15977},{"name":"莉莉","bookCount":23128}],"press":[{"name":"传记名著","bookCount":1418},{"name":"出版小说","bookCount":3380},{"name":"人文社科","bookCount":5499},{"name":"生活时尚","bookCount":471},{"name":"经管理财","bookCount":3553},{"name":"青春言情","bookCount":4159},{"name":"外文原版","bookCount":542},{"name":"政治军事","bookCount":199},{"name":"成功励志","bookCount":2587},{"name":"育儿健康","bookCount":1943}],"ok":true}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * male : [{"name":"玄幻","bookCount":454052},{"name":"奇幻","bookCount":42150},{"name":"武侠","bookCount":37203},{"name":"仙侠","bookCount":118776},{"name":"都市","bookCount":317079},{"name":"职场","bookCount":14122},{"name":"历史","bookCount":62950},{"name":"军事","bookCount":13585},{"name":"游戏","bookCount":74378},{"name":"竞技","bookCount":5266},{"name":"科幻","bookCount":101376},{"name":"灵异","bookCount":30010},{"name":"同人","bookCount":35626},{"name":"轻小说","bookCount":4484}]
         * female : [{"name":"古代言情","bookCount":383486},{"name":"现代言情","bookCount":455224},{"name":"青春校园","bookCount":99552},{"name":"纯爱","bookCount":116090},{"name":"玄幻奇幻","bookCount":117492},{"name":"武侠仙侠","bookCount":57663},{"name":"科幻","bookCount":6331},{"name":"游戏竞技","bookCount":5027},{"name":"悬疑灵异","bookCount":11650},{"name":"同人","bookCount":119975},{"name":"女尊","bookCount":15977},{"name":"莉莉","bookCount":23128}]
         * press : [{"name":"传记名著","bookCount":1418},{"name":"出版小说","bookCount":3380},{"name":"人文社科","bookCount":5499},{"name":"生活时尚","bookCount":471},{"name":"经管理财","bookCount":3553},{"name":"青春言情","bookCount":4159},{"name":"外文原版","bookCount":542},{"name":"政治军事","bookCount":199},{"name":"成功励志","bookCount":2587},{"name":"育儿健康","bookCount":1943}]
         * ok : true
         */

        private boolean ok;
        private List<MaleBean> male;
        private List<FemaleBean> female;
        private List<PressBean> press;

        public boolean isOk() {
            return ok;
        }

        public void setOk(boolean ok) {
            this.ok = ok;
        }

        public List<MaleBean> getMale() {
            return male;
        }

        public void setMale(List<MaleBean> male) {
            this.male = male;
        }

        public List<FemaleBean> getFemale() {
            return female;
        }

        public void setFemale(List<FemaleBean> female) {
            this.female = female;
        }

        public List<PressBean> getPress() {
            return press;
        }

        public void setPress(List<PressBean> press) {
            this.press = press;
        }

        public static class MaleBean {
            /**
             * name : 玄幻
             * bookCount : 454052
             */

            private String name;
            private int bookCount;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getBookCount() {
                return bookCount;
            }

            public void setBookCount(int bookCount) {
                this.bookCount = bookCount;
            }
        }

        public static class FemaleBean {
            /**
             * name : 古代言情
             * bookCount : 383486
             */

            private String name;
            private int bookCount;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getBookCount() {
                return bookCount;
            }

            public void setBookCount(int bookCount) {
                this.bookCount = bookCount;
            }
        }

        public static class PressBean {
            /**
             * name : 传记名著
             * bookCount : 1418
             */

            private String name;
            private int bookCount;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getBookCount() {
                return bookCount;
            }

            public void setBookCount(int bookCount) {
                this.bookCount = bookCount;
            }
        }
    }

    @Override
    public String toString() {
        return "Test{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
