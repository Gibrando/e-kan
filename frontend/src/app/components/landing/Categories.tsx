import React from 'react'

const Categories = () => {
  return (
    <div className="w-full flex items-center justify-center mt-24 h-[600px]">
      <div className="max-w-screen-xl w-full h-full p-8 lg:px-0 flex flex-col items-center justify-center">
        <h1 className="text-3xl font-black text-center">Kategori ikan</h1>
        <p className="mt-2 text-xl text-darkaqua text-center">Kami menjual tiga jenis ikan yang dapat Anda pilih!</p>
        <div className="flex items-center justify-center w-full mt-12">
          <div className="flex flex-col items-center justify-center">
            <img src={"/benih.jpg"} alt="benih" className="w-[200px] rounded-full custom-categories-image-style" />
            <p className="mt-4 text-xl text-darkaqua">Benih</p>
          </div>
          <div className="flex flex-col items-center justify-center mx-6 md:mx-12 lg:mx-16">
            <img src={"/caloninduk.jpg"} alt="benih" className="w-[200px] rounded-full custom-categories-image-style" />
            <p className="mt-4 text-xl text-darkaqua">Calon Induk</p>
          </div>
          <div className="flex flex-col items-center justify-center">
            <img src={"/induk.jpg"} alt="benih" className="w-[200px] rounded-full custom-categories-image-style" />
            <p className="mt-4 text-xl text-darkaqua">Induk</p>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Categories