package bg.softuni.pochivka.repository;

import bg.softuni.pochivka.model.entity.Comfort;
import bg.softuni.pochivka.model.entity.Hotel;
import bg.softuni.pochivka.model.entity.Town;
import bg.softuni.pochivka.model.enums.Accommodation;
import bg.softuni.pochivka.model.enums.HotelCategory;
import bg.softuni.pochivka.model.enums.RegionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findDistinctAllByAccommodationInAndCategoryInAndComfortsIn(List<Accommodation> accommodations, List<HotelCategory> categories, List<Comfort> comforts);

    List<Hotel> findDistinctAllByNameAndAccommodationInAndCategoryInAndComfortsIn(String name, List<Accommodation> accommodations, List<HotelCategory> categories, List<Comfort> comforts);

    List<Hotel> findAllByAccommodationInAndCategoryInAndComfortsIn(List<Accommodation> accommodations, List<HotelCategory> categories, List<Comfort> comforts);

    List<Hotel> findAllByNameAndAccommodationInAndCategoryInAndComfortsIn(String name, List<Accommodation> accommodations, List<HotelCategory> categories, List<Comfort> comforts);

    List<Hotel> findDistinctAllByAccommodationInAndCategoryIn(List<Accommodation> accommodations, List<HotelCategory> categories);

    List<Hotel> findDistinctAllByNameAndAccommodationInAndCategoryIn(String name, List<Accommodation> accommodations, List<HotelCategory> categories);

    List<Hotel> findDistinctAllByAccommodationInAndCategoryIsNullAndComfortsIn(List<Accommodation> accommodations, List<Comfort> comforts);

    List<Hotel> findAllByAccommodationInAndCategoryIsNull(List<Accommodation> accommodations);

    List<Hotel> findDistinctAllByNameAndAccommodationInAndCategoryIsNullAndComfortsIn(String name, List<Accommodation> accommodations, List<Comfort> comforts);

    List<Hotel> findAllByNameAndAccommodationInAndCategoryIsNull(String name, List<Accommodation> accommodations);

    List<Hotel> findAllByAccommodationInAndCategoryIn(List<Accommodation> accommodations, List<HotelCategory> categories);

    List<Hotel> findAllByNameAndAccommodationInAndCategoryIn(String name, List<Accommodation> accommodations, List<HotelCategory> categories);

    List<Hotel> findAllByAccommodationInAndCategoryIsNullAndComfortsIn(List<Accommodation> accommodations, List<Comfort> comforts);

    List<Hotel> findAllByNameAndAccommodationInAndCategoryIsNullAndComfortsIn(String name, List<Accommodation> accommodations, List<Comfort> comforts);

    List<Hotel> findDistinctAllByTownAndAccommodationInAndCategoryInAndComfortsIn(Town town, List<Accommodation> accommodations, List<HotelCategory> categories, List<Comfort> comforts);

    List<Hotel> findAllByTownAndAccommodationInAndCategoryInAndComfortsIn(Town town, List<Accommodation> accommodations, List<HotelCategory> categories, List<Comfort> comforts);

    List<Hotel> findDistinctAllByTownAndAccommodationInAndCategoryIsNullAndComfortsIn(Town town, List<Accommodation> accommodations, List<Comfort> comforts);

    List<Hotel> findAllByTownAndAccommodationInAndCategoryIsNullAndComfortsIn(Town town, List<Accommodation> accommodations, List<Comfort> comforts);

    List<Hotel> findDistinctAllByTownAndAccommodationInAndCategoryIn(Town town, List<Accommodation> accommodations, List<HotelCategory> categories);

    List<Hotel> findAllByTownAndAccommodationInAndCategoryIn(Town town, List<Accommodation> accommodations, List<HotelCategory> categories);

    List<Hotel> findAllByTownAndAccommodationInAndCategoryIsNull(Town town, List<Accommodation> accommodations);

    List<Hotel> findDistinctAllByNameAndTownAndAccommodationInAndCategoryInAndComfortsIn(String name, Town town, List<Accommodation> accommodations, List<HotelCategory> categories, List<Comfort> comforts);

    List<Hotel> findAllByNameAndTownAndAccommodationInAndCategoryInAndComfortsIn(String name, Town town, List<Accommodation> accommodations, List<HotelCategory> categories, List<Comfort> comforts);

    List<Hotel> findDistinctAllByNameAndTownAndAccommodationInAndCategoryIsNullAndComfortsIn(String name, Town town, List<Accommodation> accommodations, List<Comfort> comforts);

    List<Hotel> findAllByNameAndTownAndAccommodationInAndCategoryIsNullAndComfortsIn(String name, Town town, List<Accommodation> accommodations, List<Comfort> comforts);

    List<Hotel> findDistinctAllByNameAndTownAndAccommodationInAndCategoryIn(String name, Town town, List<Accommodation> accommodations, List<HotelCategory> categories);

    List<Hotel> findAllByNameAndTownAndAccommodationInAndCategoryIn(String name, Town town, List<Accommodation> accommodations, List<HotelCategory> categories);

    List<Hotel> findAllByNameAndTownAndAccommodationInAndCategoryIsNull(String name, Town town, List<Accommodation> accommodations);

    List<Hotel> findAllByTownRegionTypeRegion(RegionEnum sea);

    List<Hotel> findAllByAccommodation(Accommodation allInclusive);
}
