"use client";

import axios from "axios";
import { group } from "console";
import { useSession } from "next-auth/react";
import React, { useEffect, useState } from "react";

const PesananMasuk = () => {
  const { data: session } = useSession();
  const [items, setItems] = useState([]);
  const [groupedItems, setGroupedItems] = useState({});

  useEffect(() => {
    const fetchInvoice = async () => {
      try {
        const response = await axios.get(
          `${process.env.API_BASEURL}/api/items/process`,
          {
            headers: {
              Authorization: `Bearer ${session?.accessToken}`,
            },
          }
        );

        if (!response) {
          throw new Error("Some error occurred!");
        }

        const data = response.data.data;
        setItems(data);
      } catch (error) {
        console.log(error);
      }
    };
    fetchInvoice();
  }, [session]);

  useEffect(() => {
    if (items.length > 0) {
      // Group items by idInvoice where isDelivered is false
      const grouped = items.reduce((acc, item) => {
        if (!item.isDelivered) {
          const { idInvoice } = item;
          if (!acc[idInvoice]) {
            acc[idInvoice] = [];
          }
          acc[idInvoice].push(item);
        }
        return acc;
      }, {});
      setGroupedItems(grouped);
    }
  }, [items]);
  

  const handleDeliver = async (idInvoice) => {
    try {
      await axios.put(
        `${process.env.API_BASEURL}/api/items/process/${idInvoice}`,
        {}, // Pass an empty object as the payload if none is required
        {
          headers: {
            Authorization: `Bearer ${session?.accessToken}`,
          },
        }
      );
      
      // Remove delivered items from the state
      setGroupedItems((prevGroupedItems) => {
        const updatedGroupedItems = { ...prevGroupedItems };
        delete updatedGroupedItems[idInvoice];
        return updatedGroupedItems;
      });
      console.log(`Invoice ${idInvoice} delivered successfully.`);
    } catch (error) {
      console.log(`Failed to deliver invoice ${idInvoice}:`, error);
    }
  };

  return (
    <div className="w-full flex flex-col items-center justify-center">
      <h1 className="text-2xl font-bold mb-4">Pesanan Masuk</h1>

      {/* Render grouped items */}
      {Object.entries(groupedItems).map(([idInvoice, items]) => (
        <div key={idInvoice} className="w-full border rounded-md p-4 mb-4 shadow">
          <h2 className="text-xl font-semibold mb-2">Invoice ID: {idInvoice}</h2>
          <ul>
            {items.map((item) => (
              <li key={item.idProcessItems} className="ml-4 list-disc">
                {item.itemModel.nama}
              </li>
            ))}
          </ul>
          <button
            onClick={() => handleDeliver(idInvoice)}
            className="mt-4 bg-darkaqua text-white px-4 py-2 rounded hover:bg-black transition-colors"
          >
            Deliver
          </button>
        </div>
      ))}
    </div>
  );
};

export default PesananMasuk;
