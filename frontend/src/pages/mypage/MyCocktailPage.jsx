import Container from "../../components/common/Container";
import Headerwb from "../../components/common/Headerwb";
import styles from "./MyCocktailPage.module.css";
import s from "classnames";
import arrow_none from "../../assets/common/arrow5.png";
import arrow_active from "../../assets/common/arrow4.png";
import { useState, useEffect } from "react";
import ReviewItem from "../../components/my/ReviewItem";
import { cocktailmereview } from "../../api/Cocktail";
import { getreview } from "../../api/Review";
import useUserStore from "../../store/useUserStore";

export default function MyCocktailPage() {
  const user = useUserStore((state) => state.user);
  const [order, setOrder] = useState(true);
  const [rotate, setrotate] = useState([0, 0]);
  const [list, setList] = useState([]);
  const [page, setPage] = useState(0);

  useEffect(() => {
    async function fetchlikeData() {
      try {
        const response = await cocktailmereview(user.memberId);
        console.log(response);
        setList([...list, ...response.data]);
      } catch (error) {
        console.error("데이터불러오기실패");
      }
    }
    fetchlikeData();
  }, [page]);

  const changeOrder = (num) => {
    if (num === 0 && !order) {
      setOrder(true);
      setrotate([0, 0]);
    } else if (num === 1 && order) {
      setOrder(false);
      setrotate([0, 0]);
    } else if (num === 0 && order) {
      if (rotate[0] === 0) {
        setrotate([180, 0]);
      } else {
        setrotate([0, 0]);
      }
    } else if (num === 1 && !order) {
      if (rotate[1] === 0) {
        setrotate([0, 180]);
      } else {
        setrotate([0, 0]);
      }
    }
  };

  return (
    <Container>
      <Headerwb title={"내가 마신 칵테일"} />
      <div className={styles.main}>
        <div className={styles.order}>
          <div className={styles.order_item} onClick={() => changeOrder(0)}>
            <div
              className={s(
                styles.order_text,
                order ? styles.active_order : styles.none_order
              )}
            >
              날짜순
            </div>
            <img
              src={order ? arrow_active : arrow_none}
              alt="arrow"
              className={styles.order_img}
              style={{ transform: `rotate(${rotate[0]}deg)` }}
            />
          </div>
          <div className={styles.order_item} onClick={() => changeOrder(1)}>
            <div
              className={s(
                styles.order_text,
                !order ? styles.active_order : styles.none_order
              )}
            >
              평점순
            </div>
            <img
              src={!order ? arrow_active : arrow_none}
              alt="arrow"
              className={styles.order_img}
              style={{ transform: `rotate(${rotate[1]}deg)` }}
            />
          </div>
        </div>
        <div>
          {list.map((item) => (
            <div className={styles.item}>
              <ReviewItem item={item} setList={setList} />
            </div>
          ))}
        </div>
      </div>
    </Container>
  );
}
